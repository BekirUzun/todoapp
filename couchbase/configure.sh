set -m

/entrypoint.sh couchbase-server &

echo "Waiting Couchbase to load..." |& grep Couchbase
sleep 15

status_code=$(curl --write-out %{http_code} --silent --output /dev/null --stderr /dev/null -u  $COUCHBASE_ADMINISTRATOR_USERNAME:$COUCHBASE_ADMINISTRATOR_PASSWORD http://127.0.0.1:8091/pools/default/buckets/default)

if [[ "$status_code" -ne 200 ]] ; then
  echo "Initial run: Autoconfiguring Couchbase!" |& grep Initial

  echo "Configuring admin password..." |& grep Configuring
  curl -s --output /dev/null -v -u Administrator:password http://127.0.0.1:8091/settings/web -d port=8091 -d username=$COUCHBASE_ADMINISTRATOR_USERNAME -d password=$COUCHBASE_ADMINISTRATOR_PASSWORD

  echo "Configuring admin roles..." |& grep Configuring
  couchbase-cli user-manage -c 127.0.0.1:8091 -u $COUCHBASE_ADMINISTRATOR_USERNAME -p $COUCHBASE_ADMINISTRATOR_PASSWORD --set --auth-domain local --rbac-username $COUCHBASE_ADMINISTRATOR_USERNAME --rbac-password $COUCHBASE_ADMINISTRATOR_PASSWORD --roles admin,query_manage_index[*]

  echo "Configuring memory quota..." |& grep Configuring
  curl -s --output /dev/null -v -u $COUCHBASE_ADMINISTRATOR_USERNAME:$COUCHBASE_ADMINISTRATOR_PASSWORD -X POST http://127.0.0.1:8091/pools/default -d memoryQuota=512 -d indexMemoryQuota=512

  echo "Configuring services..." |& grep Configuring
  curl -s --output /dev/null -v -u $COUCHBASE_ADMINISTRATOR_USERNAME:$COUCHBASE_ADMINISTRATOR_PASSWORD http://127.0.0.1:8091/node/controller/setupServices -d services=kv%2cn1ql%2Cindex

  echo "Configuring index storage mode..." |& grep Configuring
  curl -s --output /dev/null  -i -u $COUCHBASE_ADMINISTRATOR_USERNAME:$COUCHBASE_ADMINISTRATOR_PASSWORD -X POST http://127.0.0.1:8091/settings/indexes -d 'storageMode=memory_optimized'

  echo "Configuring bucket..." |& grep Configuring
  curl -s --output /dev/null  -v -u $COUCHBASE_ADMINISTRATOR_USERNAME:$COUCHBASE_ADMINISTRATOR_PASSWORD -X POST http://127.0.0.1:8091/pools/default/buckets -d name=$COUCHBASE_BUCKET -d bucketType=couchbase -d ramQuotaMB=128 -d authType=sasl -d saslPassword=$COUCHBASE_BUCKET_PASSWORD

  # wait bucket initialization
  bucketStatus="init"
  while [[ "$bucketStatus" != "healthy" ]]
  do
    sleep 3
    bucketStatus=$(curl -s -u $COUCHBASE_ADMINISTRATOR_USERNAME:$COUCHBASE_ADMINISTRATOR_PASSWORD http://127.0.0.1:8091/pools/default/buckets/default | grep -o '"status":"[^"]*' | grep -o '[^"]*$' | xargs)
    echo "Waiting bucket status to be healthy. Current status: $bucketStatus" |& grep a
  done

  echo "Configuring bucket index..." |& grep Configuring
  curl -s --output /dev/null -v -u $COUCHBASE_ADMINISTRATOR_USERNAME:$COUCHBASE_ADMINISTRATOR_PASSWORD http://127.0.0.1:8093/query/service -d "statement=CREATE PRIMARY INDEX defaultpk ON default USING GSI"

fi

echo "Couchbase started successfully." |& grep Couchbase

fg 1

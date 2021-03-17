(ns lambda.core
  (:require
    [lambda.impl.runtime :as runtime]
    [babashka.pods :as pods]))

; (pods/load-pod 'org.babashka/aws "0.0.5")
(pods/load-pod "./pod-babashka-aws")
(require '[pod.babashka.aws :as aws])

(defn my-assoc [m k v]
  (let [s3-client (aws/client {:api :s3 :region "us-east-1"})]
    (assoc m k v
          :bucket (aws/invoke s3-client {:op :ListBuckets}))))

(defn -main [& _]
  (let [k :test
        v (str "test-" (rand-int 1000))
        ;; input to handler-fn is a parsed request body, something like
        ;; {:id cdc73f9d-aea9-11e3-9d5a-835b769c0d9c, :detail-type Scheduled Event, :source aws.events, :time 2020-01-01T03:33:00Z, :region eu-west-1, ...}
        handler-fn (fn [request] (my-assoc request k v))]
    (runtime/init handler-fn)))

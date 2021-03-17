#!/usr/bin/env bb

(require '[babashka.pods :as pods])

; (pods/load-pod 'org.babashka/aws "0.0.5")
(pods/load-pod "./pod-babashka-aws")

(require '[pod.babashka.aws :as aws])

(def s3-client
  (aws/client {:api :s3 :region "us-east-1"}))

(aws/invoke s3-client {:op :ListBuckets})

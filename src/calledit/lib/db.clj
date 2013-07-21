;;
;; Connection pooling.
;; http://clojure.github.io/java.jdbc/doc/clojure/java/jdbc/ConnectionPooling.html
;;
(ns calledit.lib.db
  (:use
   [clojure.tools.logging :only (info error)]
   )
  (:require [clojure.java.jdbc :as sql]
              [korma.db :as db]
              [clojure.string :as string])

  (:import (java.net URI)))


(defn connect-db []
  "split up DATABASE_URL into constituent parts so that korma likes it."
  (let
      [db-uri-str (System/getenv "DATABASE_URL")
       db-uri (java.net.URI. db-uri-str)]
    (->> (string/split (.getUserInfo db-uri) #":")
         (#(identity {:db (last (string/split db-uri-str #"\/"))
                      :host (.getHost db-uri)
                      :port (.getPort db-uri)
                      :user (% 0)
                      ;:make-pool? true
                      :password (% 1)}))
         (db/postgres)
         (db/defdb pgdb)
         )))


(ns calledit.lib.util
  (:require
   [clojure.string :as str]))


(defn current-date []
  (new java.util.Date))


(defn parse-sql-date [dt]
  (if-not (str/blank? dt)
    (new java.sql.Date (.getTime (.parse (new java.text.SimpleDateFormat "yyyy-MM-dd") dt)))))


(defn parse-date [dt]
  (if-not (str/blank? dt)
    (.parse (new java.text.SimpleDateFormat "yyyy-MM-dd") dt)))


(defn format-date [dt]
  (if-not (nil? dt)
    (.format (new java.text.SimpleDateFormat "yyyy-MM-dd") dt)))


(defn current-date-str []
  (format-date (current-date)))


(defn uuid []
  "Generate a base 36 string from a UUID
  90a58d7a-d274-4dc4-a80e-37daa76b7fb8 --> 2odnrp7yitnl05rg975gmyeas"
  (.toString (new BigInteger (.replace (str (java.util.UUID/randomUUID)) "-" "") 16) 36))



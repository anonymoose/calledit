;;
;; Data model for the "call" table
;;
(ns calledit.model.call
  (:use
   [korma.db]
   [korma.core])
  (:require
   [calledit.lib.util :as util]))


(defentity ci_call
  (pk :call_id)
  (transform (fn [{create_dt :create_dt :as v}]
               (if create_dt
                 (assoc v :create_dt (util/format-date create_dt)) v)))
  )


(defn find-by-email [email]
  (select ci_call
          (where {:email email
                  :delete_dt nil})))


(defn find-by-id [call_id]
  (first (select ci_call
                 (where {:call_id call_id
                         :delete_dt nil}))))


(defn find-last-n [n]
  (select ci_call
          (where {:delete_dt nil})
          (order :create_dt :ASC)
          (limit n)))


(defn save [call_id email prediction prediction_dt]
  (update ci_call
          (set-fields {:call_id call_id
                       :email email
                       :prediction prediction
                       :prediction_dt (util/parse-sql-date prediction_dt)})
          (where {:call_id call_id})))


(defn create [email prediction prediction_dt]
  (insert ci_call
          (values {:call_id (util/uuid)
                   :email email
                   :prediction prediction
                   :prediction_dt (util/parse-sql-date prediction_dt)})))



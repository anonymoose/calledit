;;
;; controller layer.  Logic goes in here.
;;
(ns calledit.controller.site
  (:use
   [calledit.lib.template :only [render-page]]
   [clojure.tools.logging :only (info error)]
   )
  (:require
   [clojure.string :as str]
   [calledit.model.user :as user]
   [calledit.model.call :as call]
   [ring.util.response :as ring]
   [calledit.lib.util :as util])
  )


(defn -common-render
  [page vars]
  "Render everything with header, footer and common variables."
  (render-page page
               (merge vars
                      {:today util/current-date-str
                       })
               [:header :footer]))


(defn index []
  "Home Page. Dredge up the last 10 or so for posterity."
  (-common-render "index" {:last-N (call/find-last-n 10)}))


(defn about []
  (-common-render "about" {}))


(defn call-view [call_id]
  "Just view it with no edit fields."
  (let [c (call/find-by-id call_id)]
    (-common-render "call.view" {:call c
                                 :call_id (:call_id c)
                                 :prediction (:prediction c)
                                 :prediction_dt (:prediction_dt c)
                                 :headline (rand-nth ["So let it be written. So let it be done."
                                                      "You said it.  Were you right?"])
                                 :email (:email c)})))


(defn call-edit 
  ([]
     "Show the empty prediction form" 
     (-common-render "call.edit" {:prediction_dt util/current-date-str
                                  :postback "/call-create"})) 
  ([call_id]
     "Show the indicated call, in editable form." 
     (let [c (call/find-by-id call_id)
           call_id (:call_id c)]
       (-common-render "call.edit" {:call c
                                    :call_id (:call_id c)
                                    :postback "/call-save"
                                    :link #(str "This is your link to prove you're not lying. http://i-called.it/" %)
                                    :prediction (:prediction c)
                                    :prediction_dt (:prediction_dt c)
                                    :email (:email c)}))))


(defn call-save
  ([email prediction prediction_dt call_id]
     "Save an existing 'call' record."
     (do
       (call/save call_id email prediction prediction_dt)
       (ring/redirect (str "/" call_id))))
  ([email prediction prediction_dt]
     "Save new 'call' record."
     (when-not (and (str/blank? prediction) (str/blank? prediction))
         (let [call_id (:call_id (call/create email prediction prediction_dt))]
           (ring/redirect (str "/" call_id)))))) 


(defn call-find [email]
  "Search by email"
  (let [calls (call/find-by-email email)]
    (if (< 0 (count calls))
      (-common-render "call.list" {:email email
                                   :calls calls})
      (-common-render "call.empty" {:email email}))))



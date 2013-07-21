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
  "Render everything with header, footer and common variables."
  [page vars]
  (render-page page
               (merge vars
                      {:today util/current-date-str
                       ;:convert-to-dt #(subs (str %) 0 19)
                       :convert-to-dt #(str (type %))
                       })
               [:header :footer]))


(defn index []
  "Home Page"
  (-common-render "index" {}))


(defn call-view [call_id]
  "Just view it with no edit fields."
  (let [c (call/find-by-id call_id)]
    (-common-render "call.view" {:call c
                                 :call_id (:call_id c)
                                 :prediction (:prediction c)
                                 :prediction_dt (:prediction_dt c)
                                 :email (:email c)})
    ))
  

;; Show the prediction form
(defn call-edit 
  ([]
     (-common-render "call.edit" {:prediction_dt util/current-date-str
                                  :postback "/call-create"})) 
  ([call_id]
     (let [c (call/find-by-id call_id)
           call_id (:call_id c)]
       (-common-render "call.edit" {:call c
                                    :call_id (:call_id c)
                                    :postback "/call-save"
                                    :link #(str "This is your link to prove you're not lying. http://i-called.it/" %)
                                    :prediction (:prediction c)
                                    :prediction_dt (:prediction_dt c)
                                    :email (:email c)})
       )))


(defn call-save
  "Save a 'call' record."
  ([email prediction prediction_dt call_id]
     (do
       (call/save call_id email prediction prediction_dt)
       (ring/redirect (str "/call-edit/" call_id))))
  ([email prediction prediction_dt]
     (when-not (and (str/blank? prediction) (str/blank? prediction))
         (let [call_id (:call_id (call/create email prediction prediction_dt))]
           (ring/redirect (str "/call-edit/" call_id)))))) 


(defn call-find [email]
  (error "called find. inner")
  (-common-render "call.list" {:email email
                               :convert-dt #()
                               :calls (call/find-by-email email)}))



;;
;; Connection pooling.
;; http://clojure.github.io/java.jdbc/doc/clojure/java/jdbc/ConnectionPooling.html
;;
(ns calledit.lib.db
  (:use korma.db)
  (:import com.mchange.v2.c3p0.ComboPooledDataSource))

(defdb db (postgres {:db "calledit"
                     :user "calledit"
                     :password "calledit"
                     ;; optional keys
                     :host "localhost"
                     }))



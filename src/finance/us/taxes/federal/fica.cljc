(ns finance.taxes.us.federal.fica
  (:require [finance.util :refer [add multiply to-cents to-mills]]))

(defn- times
  [rate income]
  (-> 
   income
   (to-mills)
   (multiply rate)
   (to-cents)))

(defn- fica 
  [ssn medicare income]
  (to-cents
   (add 
    (multiply (to-mills income) ssn)
    (multiply (to-mills income) medicare))))
  
; -- rates 
(def ^:private rate-social-security-2019 0.062)
(def ^:private rate-medi-care-2019 0.0145)

;; -- public
(def social-security-2019 (partial times rate-social-security-2019))
(def medi-care-2019 (partial times rate-medi-care-2019))
(def fica-2019 (partial fica rate-social-security-2019 rate-medi-care-2019)
  
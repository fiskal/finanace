(ns finance.us.taxes.ca.income
  (:require [finance.util :refer [add minus multiply to-cents to-mills]]))

; -- math
(defn- minimum [a b] (if (nil? b) a (min a b)))

(defn- income-mills
   [brackets income]
   (reduce 
    (fn [sum bracket]
      (->
       income
       (minimum (:max bracket))
       (minus (:min bracket))
       (multiply (:rate bracket))
       (max 0)
       (add sum)))
    0
    brackets))

(defn- income
   [brackets income-cents]
   (to-cents
    (income-mills
      (to-mills income-cents)
      (map 
      #(-> (update % :min to-mills)
            (update % :max to-mills))
      brackets))))

;; -- rates
(defn- bracket-mills
  [brackets]
  (map 
    #(-> (update % :min to-mills)
         (update % :max to-mills))
    brackets))

(def ^:private bracket-single-2019
  [{:min nil     :max 16555   :rate 0.01}
   {:min 16556   :max 38989   :rate 0.02}
   {:min 38990   :max 61537   :rate 0.04}
   {:min 61538   :max 85421   :rate 0.06}
   {:min 85422   :max 107959  :rate 0.08}
   {:min 107960  :max 551475  :rate 0.093}
   {:min 551476  :max 661767  :rate 0.103}
   {:min 661768  :max 999999  :rate 0.113}
   {:min 1000000 :max 1074995 :rate 0.123}
   {:min 1074996 :max nil     :rate 0.133}])


; -- public
(def single-2019 (partial income (bracket-mills bracket-single-2019)))

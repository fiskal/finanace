(ns finance.us.taxes.federal.income
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

(def ^:private bracket-single-2021
  "source: https://taxfoundation.org/2021-tax-brackets/"
  [{:min nil    :max 9950   :rate 0.1}
   {:min 9951   :max 40525  :rate 0.12}
   {:min 40526  :max 86375  :rate 0.22}
   {:min 86376  :max 164925 :rate 0.24}
   {:min 164926 :max 209425 :rate 0.32}
   {:min 209426 :max 523600 :rate 0.35}
   {:min 523601 :max nil    :rate 0.37}])

(def ^:private bracket-joint-2021
  "source: https://taxfoundation.org/2021-tax-brackets/"
  [{:min nil    :max 19900  :rate 0.1}
   {:min 19901  :max 81050  :rate 0.12}
   {:min 81051  :max 172750 :rate 0.22}
   {:min 172751 :max 329850 :rate 0.24}
   {:min 329851 :max 418850 :rate 0.32}
   {:min 418851 :max 628300 :rate 0.35}
   {:min 628301 :max nil    :rate 0.37}])

(def ^:private bracket-head-2021
  "source: https://taxfoundation.org/2021-tax-brackets/"
  [{:min nil    :max 14200  :rate 0.1}
   {:min 14201  :max 54200  :rate 0.12}
   {:min 54201  :max 86350  :rate 0.22}
   {:min 86351  :max 164900 :rate 0.24}
   {:min 164901 :max 209400 :rate 0.32}
   {:min 209401 :max 523600 :rate 0.35}
   {:min 523601 :max nil    :rate 0.37}])

(def ^:private bracket-single-2019
  [{:min nil    :max 19400  :rate 0.1}
   {:min 78951  :max 168400 :rate 0.22}
   {:min 168401 :max 321450 :rate 0.24}
   {:min 321451 :max 408200 :rate 0.32}
   {:min 408201 :max 612350 :rate 0.35}
   {:min 612351 :max nil    :rate 0.37}])


; -- public

(def single-2021 (partial income (bracket-mills bracket-single-2021)))
(def joint-2021 (partial income (bracket-mills bracket-joint-2021)))
(def head-2021 (partial income (bracket-mills bracket-head-2021)))
(def single-2019 (partial income (bracket-mills bracket-single-2019)))

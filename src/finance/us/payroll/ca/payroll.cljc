(ns finance.payroll.us.payroll.ca
  (:require [finance.us.taxes.ca.income :as ca-income]
            [finance.us.taxes.federal.income :as us-income]
            [finance.us.taxes.federal.fica :as fica]
            [finance.util :refer [minus to-cents to-mills]]))

(defn take-home
  [income]
  (let [income-mills (to-mills income)]
   (to-cents
    (minus 
     income-mills
     (to-mills (us-income/single-2019 income))
     (to-mills (fica/fica-2019 income))
     (to-mills (ca-income/single-2019 income))))))

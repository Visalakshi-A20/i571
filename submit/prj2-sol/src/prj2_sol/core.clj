(ns prj2-sol.core)

(defrecord OrderItem
  [ sku        ;keyword ID identifying item
    category   ;keyword giving item category
    n-units    ;# of units of item
    unit-price ;price per unit of item
  ])

;; #1: 15-points
;;given a list items of OrderItem, return the total for
;;the order, i.e. sum n-units*unit-price over all items
;;must be recursive but cannot use loop or recur
(defn items-total1 [items]
  (if (empty? items)
    0
    (+ (* (:n-units (first items)) (:unit-price (first items)))
       (items-total1 (rest items)))))


;; #2: 15-points
;;given a list items of OrderItem and a category,
;;return list of elements of items having specified category.
;;must be implemented using recursion
(defn items-with-category1 [items category]
  (if (empty? items)
    [] ;; base case: empty list, return empty list
    (let [current-item (first items)
          rest-of-items (rest items)]
      (if (= category (:category current-item))
        (cons current-item (items-with-category1 rest-of-items category))
        (items-with-category1 rest-of-items category))))) ;; recursive case: check rest of items


;; #3: 15-points
;;same specs as items-total1 but must be implemented using
;;loop and recur
(defn items-total2 [items]
  (loop [total 0
         items items]
    (if (empty? items)
      total
      (let [item (first items)]
        (recur (+ total (* (:n-units item) (:unit-price item)))
               (rest items))))))


;; #4: 10-points
;;given a list items of OrderItem return a list of all the :sku's
;;cannot use explicit recursion
(defn item-skus [items]
  "Return :sku's of all items"
  (map :sku items))


;; #5: 10-points
;;given a list items of OrderItem, return a list of skus of those elements
;;in items having unit-price greater than price
;;cannot use explicit recursion
(defn expensive-item-skus [items price]
  "Return list of :sku's of all items having :unit-price > price"
  (->> items
       (filter #(> (:unit-price %) price))
       (map :sku)))


;; #6: 10-points
;;same specs as items-total1 but cannot use explicit recursion
(defn items-total3 [items]
  (reduce (fn [acc item]
            (+ acc (* (:n-units item) (:unit-price item))))
          0
          items))




;; #7: 10-points
;;same spec as items-with-category1, but cannot use explicit recursion
(defn items-with-category2 [items category]
  "Return sublist of items having specified category"
  (filter #(= category (:category %)) items))


;; #8: 15-points
;;given a list items of OrderItem and an optional category
;;return total of n-units of category in items.  If the
;;optional category is not specified, return total of n-units
;;of all items.
;;cannot use explicit recursion
(defn item-category-n-units
  "return sum of :n-units of items for category (all categories if unspecified)"
  ([items]
   (reduce (fn [acc item]
             (+ acc (:n-units item)))
           0 items))
  ([items category]
   (reduce (fn [acc item]
             (if (= (:category item) category)
               (+ acc (:n-units item))
               acc))
           0 items)))

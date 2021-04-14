(ns image-slider.hello
  (:require [reagent.core :as r]))

(defn url [src]
      (str "url(" js/window.location.href src))

(def images ["images/GPTempDownload.JPG"
             "images/GPTempDownload2.jpg"])

(def interval-time 500)

(def eraserActiveTime 700)

(defonce image-index (r/atom 0))
(def eraser-class (r/atom ""))

(defn- setinterval []
       (js/setInterval (fn []
                           (reset! eraser-class "active")
                           (js/setTimeout (fn []
                                              (reset! eraser-class "")
                                              (if-not (= @image-index (dec (count images)))
                                                      (swap! image-index inc)
                                                      (reset! image-index 0)))
                                          interval-time))
                       5000))
(def auto-interval (setinterval))

(defn- manage-timer [callback]
       (reset! eraser-class "active")
       (js/setTimeout (fn []
                           (reset! eraser-class "")
                           (callback))
                      eraserActiveTime)
       (js/clearTimeout auto-interval)
       (setinterval))

(defn- to-next-image [image-index]
       (if-not (= @image-index (dec (count images)))
               (swap! image-index inc)
               (reset! image-index 0)))



(defn- slider [image-index]
       (let [
             num-images (count images)]
            [:div.slider
             [:div.slide.active
              ;{:src "/images/GPTempDownload.JPG"}
              {:style {:background-image (url (get images @image-index))}}
              ]
             [:div.buttons-container
              [:button#previous
               {:style {:background-image (url "/icons/caret_left.svg")}
                :on-click (fn [] (manage-timer
                                   #(if-not (= @image-index 0)
                                            (swap! image-index dec)
                                            (reset! image-index (dec num-images)))))}]
              [:button#next
               {:style {:background-image (url "/icons/caret_right.svg")}
                :on-click (fn []
                              (manage-timer
                                #(to-next-image image-index)))}]]
             [:div.eraser {:class @eraser-class}]]))

(defn app []
      (reset! image-index 0)                                ;initial state
      (fn []
          [slider image-index]))
"background-image: url('https://images.unsplash.com/photo-1431444393712-19267bd26144?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1191&q=80');"
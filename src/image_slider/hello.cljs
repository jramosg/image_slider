(ns image-slider.hello
  (:require [reagent.core :as r]))

(defn url [src]
      (str "url(" js/window.location.href src))

(def images ["images/GPTempDownload.jpg"
             ;  "images/GPTempDownload2.jpg"
             "images/GPTempDownload3.jpg"
             "images/GPTempDownload4.jpg"
             "images/GPTempDownload5.jpg"])

(def interval-time 500)

(def eraserActiveTime 700)

(defonce image-index (r/atom 0))
(defonce interval (r/atom nil))
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

(defn- manage-timer [callback]
       (reset! eraser-class "active")
       (js/setTimeout (fn []
                          (reset! eraser-class "")
                          (callback))
                      eraserActiveTime)
       (js/clearTimeout @interval)                          ;Stop interval
       (reset! interval (setinterval))                      ;Start interval again
       )

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
      (reset! interval (setinterval))                       ;initialize interval
      (fn []
          [slider image-index]))
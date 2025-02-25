#include "WiFi.h"
#include <WebServer.h>
#include <math.h>
#include <HTTPClient.h>

const char* ssid = "Simon Adebisi";
const char* password = "simon357";
WebServer server(8086);
 
const int termistorPin = 32;  // 32 numaralı pin analog okuma için
const int lampPin = 14;                  
bool lampState = false; 

void setup() {
  Serial.begin(9600);
  pinMode(lampPin, OUTPUT);
  digitalWrite(lampPin, LOW);  
  analogReadResolution(12);   // ardunio'da 10        

 
  WiFi.begin(ssid, password);
  
  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
  }
  // API Endpoint: /toggle
  server.on("/toggle", HTTP_GET, []() {  //webServer.h gerekli
    lampState = !lampState;             
    digitalWrite(lampPin, lampState ? HIGH : LOW);

    String status = lampState ? "ON" : "Off";
    server.send(200, "text/plain", status); 
    Serial.println("Lamba durumu: " + status);
  });

  server.begin();
  Serial.println("Sunucu başlatıldı.");
}

double Termistor(int analogOkuma) {
  const double A = 0.001129148;
  const double B = 0.000234125;
  const double C = 0.0000000876741;

  double voltaj = analogOkuma * (3.3 / 4095.0);  // 3.3V'a göre normalize et
  double R = (3.3 * 10000.0 / voltaj) - 10000.0;  // 10kΩ'luk sabit direnç ile hesaplanan termistor direnci

  double sicaklik = log(R);  // ln(R) alınır
  sicaklik = 1.0 / (A + (B * sicaklik) + (C * pow(sicaklik, 3))); // 1/T hesaplanır
  sicaklik = sicaklik - 273.15; // Kelvin'den Celsius'a dönüşüm

  return sicaklik;
}

void notifySpringAPI(double sicaklik) {
  if (WiFi.status() == WL_CONNECTED) {
    HTTPClient http; // burada esp32 kendisi istek atıyor httpClient kütüphanesi gerekiyor.
    http.begin("http://192.168.109.162:8085/notify/heat");// api 
    http.addHeader("Content-Type", "application/json");

    String jsonPayload = "{\"temperature\": " + String(sicaklik, 2) + "}";
    int httpResponseCode = http.POST(jsonPayload);

    if (httpResponseCode > 0) {
      Serial.println("Bildirim gönderildi: " + String(httpResponseCode));
    } else {
      Serial.println("Hata: " + http.errorToString(httpResponseCode));
    }
    http.end();
  } else {
    Serial.println("WiFi bağlantısı yok.");
  }
}



void loop() {
  server.handleClient(); 
  delay(50);

   int deger = analogRead(termistorPin);  
  double sicaklik = Termistor(deger);    
  Serial.println(sicaklik);              

  if(sicaklik > 21) {
    digitalWrite(lampPin, HIGH);
    notifySpringAPI(sicaklik);  
    delay(10000);
  }
  /*else {
    digitalWrite(lampPin, LOW);   
  }*/

  delay(50); 

}

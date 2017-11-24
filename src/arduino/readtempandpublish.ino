int tempPort = A0;

void setup()
{
  Serial.begin(1);
  pinMode(tempPort, INPUT);
}

void loop()
{
  int value = analogRead(tempPort);
  Serial.print(value);
  delay(100);
}
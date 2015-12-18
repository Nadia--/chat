Chat client using Socket.io for Android:

1. Get the Android Development Environment
http://developer.android.com/training/basics/firstapp/index.html

2. Import BitcoinMiner (ignore the project name, we were making a bitcoin miner but now it's a chat app)

3. Only important files are:
  - (implementation) BitcoinMiner\app\src\main\java\com\example\bitcoinminer\MainActivity.java
  - (layout) BitcoinMiner\app\src\main\res\layout\activity_main.xml
  - (manifest) BitcoinMiner\app\src\main\AndroidManifest.xml

4. The client requires a server to connect to. We had one up on DigitalOcean but no guarantees on it being up and running - http://45.55.170.98:3000/

5. If not, take the server code and host it somewhere. Put in the address for your server in MainActivity.java.

6. The server written in node.js requires express.

7. Build the project and run it on a phone or emulator.

8. Chat!

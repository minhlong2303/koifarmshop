// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { GoogleAuthProvider } from "firebase/auth";
import { getStorage } from "firebase/storage";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
  apiKey: "AIzaSyArPEN2WxWsXYh0qrQ74gvtKTKoBVkYGe0",
  authDomain: "koifarmshop-6cae6.firebaseapp.com",
  projectId: "koifarmshop-6cae6",
  storageBucket: "koifarmshop-6cae6.appspot.com",
  messagingSenderId: "16308657905",
  appId: "1:16308657905:web:1405671b29e1a3091e9350",
  measurementId: "G-5FL91PD663",
};
// Initialize Firebase
const app = initializeApp(firebaseConfig);
const storage = getStorage(app);
const googleProvider = new GoogleAuthProvider();

export { storage, googleProvider };

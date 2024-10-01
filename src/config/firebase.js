// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { GoogleAuthProvider } from "firebase/auth";
import { getStorage } from "firebase/storage";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
  apiKey: "AIzaSyB3PCKyxedfwdlF2GHM71UpKMxylA1wK_4",
  authDomain: "fir-7579f.firebaseapp.com",
  projectId: "fir-7579f",
  storageBucket: "fir-7579f.appspot.com",
  messagingSenderId: "90206809631",
  appId: "1:90206809631:web:d4a5160185b89bc0f9ef2a",
  measurementId: "G-7C24GNMY92"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const storage = getStorage(app);
const googleProvider = new GoogleAuthProvider() 

export { storage,googleProvider};

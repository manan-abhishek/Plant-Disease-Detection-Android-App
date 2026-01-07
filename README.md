🌿 AI-Powered Plant Disease Detection System

🔗 Live Demo (Web App):
https://plant-disease-detection-system-uyx4.onrender.com/

📌 Problem Statement

Farmers often face challenges in identifying plant diseases at an early stage, leading to reduced crop yield and economic loss. Manual disease identification requires expert knowledge, which is not always available in rural or remote areas. Therefore, there is a need for an automated, mobile-based solution that can instantly detect plant diseases using leaf images.

🎯 Objectives

To build an Android application capable of detecting plant diseases using Artificial Intelligence

To classify healthy and diseased plant leaves using a trained Machine Learning model

To provide disease details along with suggested remedies

To enable offline predictions using TensorFlow Lite

To help farmers improve crop health and agricultural productivity

🏗️ System Architecture
Components

User Interface (UI): Android application built using Kotlin with Camera and Gallery access

Image Preprocessing: Image resizing and normalization for model compatibility

Machine Learning Model: Pretrained CNN model (TensorFlow / Keras) converted to .tflite

Prediction Module: Loads the TFLite model and predicts the disease class

Result Display: Displays disease name, confidence score, and suggested remedies

Architecture Flow
User → Image Capture → Preprocessing → ML Model (TFLite) → Prediction → Result UI

🧠 Machine Learning Model Explanation
1️⃣ Problem Definition

Plants are affected by various diseases, and early identification is critical for preventing crop damage. This system uses Machine Learning to automatically detect plant diseases from leaf images.

2️⃣ Dataset

Dataset Used: PlantVillage Dataset

Source: Kaggle

Contains thousands of labeled leaf images of healthy and diseased plants

3️⃣ Data Preprocessing

Images resized to 224 × 224 pixels

Pixel values normalized

Dataset split into:

80% Training

20% Validation

4️⃣ Model Architecture

Model Used: MobileNetV2 (Transfer Learning)

Pretrained on millions of images

Custom classification layers added for plant disease detection

Why MobileNetV2?

Lightweight and fast

Optimized for mobile devices

Suitable for real-time inference

5️⃣ Training Process

Trained for multiple epochs

Achieved approximately 90–95% accuracy on validation data

6️⃣ Model Output

The model predicts:

Disease Name

Confidence Score

Example Output:

Predicted Disease: Potato___Early_blight
Confidence: 66%

7️⃣ Android Integration

Trained model converted to TensorFlow Lite (.tflite)

Class labels stored in labels.txt

Enables offline predictions without internet access

📱 Application Features

Capture or upload leaf images

Real-time disease prediction

Confidence score display

Remedy suggestions

Offline functionality using TFLite

Simple and farmer-friendly UI

✅ Results / Output

Successfully identifies plant diseases from captured images

Displays prediction instantly with confidence level

Example:

Input: Tomato leaf image

Output: Tomato Leaf Spot

Confidence: 96%

Remedy: Use organic fungicide and ensure proper irrigation

🏁 Conclusion

The AI-Powered Plant Disease Detection System offers an efficient and accessible solution for farmers to identify plant diseases in real time. By leveraging Machine Learning and mobile technology, the system reduces dependency on expert consultation, enables quick corrective action, and ultimately improves agricultural productivity and crop yield.

🚀 Future Enhancements

Support for more crop types

Multi-language support for farmers

Cloud-based disease history tracking

AI-based fertilizer recommendations

🧑‍💻 Developer

Abhishek Kumar Nayak
Android Developer | Machine Learning | TensorFlow Lite
🔗 LinkedIn: https://www.linkedin.com/in/abhishekkumarnayak11

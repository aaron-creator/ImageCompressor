# ImageCompressor

ImageCompressor is a full-stack image compression project built using **Spring Boot** for the backend and **Angular** for the frontend. The application allows users to upload an image, preview it, compress it based on selected width, height, and quality values, and then view the compressed result with original and compressed file sizes.

---

## Features

- Upload image from the browser
- Preview selected image before compression
- Compress image using custom quality percentage
- Resize image using maximum width and height
- Display original image size and compressed image size
- Download the compressed image
- Simple REST API using Spring Boot
- Angular component-based frontend structure

---

## Tech Stack

### Backend

- Java 21 / Java 17
- Spring Boot
- Spring Web
- Multipart File Upload
- ImageIO / Java image processing APIs
- Maven

### Frontend

- Angular
- TypeScript
- HTML
- CSS
- Angular Services
- Reactive component communication

---

## Project Structure

```text
ImageCompressor
│
├── backend
│   └── image-compressor-api
│       ├── src/main/java
│       │   └── com/example/imagecompressor
│       │       ├── controller
│       │       │   └── ImageController.java
│       │       ├── service
│       │       │   └── ImageCompressionService.java
│       │       ├── dto
│       │       │   └── CompressionResponse.java
│       │       └── ImageCompressorApplication.java
│       │
│       ├── src/main/resources
│       │   └── application.properties
│       │
│       └── pom.xml
│
└── frontend
    └── image-compressor-ui
        ├── src/app
        │   ├── components
        │   │   ├── image-upload
        │   │   ├── image-preview
        │   │   └── compression-result
        │   │
        │   ├── services
        │   │   └── image-compression.service.ts
        │   │
        │   └── app.component.ts
        │
        └── package.json
```

---

## Backend API

### Compress Image

```http
POST /api/images/compress
```

### Request Type

```text
multipart/form-data
```

### Request Parameters

| Parameter | Type | Description |
|---|---|---|
| file | File | Image file to be uploaded |
| maxWidth | Integer | Maximum width of compressed image |
| maxHeight | Integer | Maximum height of compressed image |
| quality | Float | Compression quality value between 0 and 1 |

### Example Request in Postman

Use `form-data` in the Body tab:

| Key | Value |
|---|---|
| file | Select image file |
| maxWidth | 800 |
| maxHeight | 600 |
| quality | 0.6 |

### Example Response

```json
{
  "fileName": "compressed-image.jpg",
  "originalSize": "700.97 KB",
  "compressedSize": "63.65 KB",
  "downloadUrl": "/api/images/download/compressed-image.jpg"
}
```

---

## Angular Components

### 1. Image Upload Component

Used to select the image file from the user's system.

```bash
ng g c components/image-upload
```

Responsibilities:

- Accept image input from user
- Validate selected file
- Pass selected file to parent component or service

---

### 2. Image Preview Component

Used to show the selected image before compression.

```bash
ng g c components/image-preview
```

Responsibilities:

- Display image preview
- Show selected file name
- Improve user experience before submitting the image

---

### 3. Compression Result Component

Used to show the output after compression.

```bash
ng g c components/compression-result
```

Responsibilities:

- Show original image size
- Show compressed image size
- Show compression percentage
- Provide download option

---

## How to Run the Project

### Backend Setup

Go to the backend project folder:

```bash
cd backend/image-compressor-api
```

Run the Spring Boot application:

```bash
mvn spring-boot:run
```

The backend will start at:

```text
http://localhost:8080
```

---

### Frontend Setup

Go to the Angular project folder:

```bash
cd frontend/image-compressor-ui
```

Install dependencies:

```bash
npm install
```

Run Angular application:

```bash
ng serve
```

The frontend will start at:

```text
http://localhost:4200
```

---

## Application Flow

```text
User selects image
        ↓
Image preview is displayed
        ↓
User enters width, height, and quality
        ↓
Angular sends image to Spring Boot API
        ↓
Spring Boot compresses and resizes image
        ↓
Compressed image details are returned
        ↓
User views result and downloads compressed image
```

---

## Why This Project Is Useful

This project helps reduce image file size while keeping acceptable image quality. It can be used in real-world applications where users upload images, such as:

- Portfolio websites
- E-commerce product image upload
- Profile picture upload
- Blog image optimization
- Document upload systems
- Cloud storage optimization

---

## Future Enhancements

- Add AI-based compression quality suggestion
- Add drag-and-drop image upload
- Support multiple image compression
- Show before-and-after image comparison
- Add image format conversion, such as PNG to JPEG
- Store compression history in database
- Add user login and dashboard
- Deploy backend and frontend to cloud

---

## AI Tool Enhancement Idea

This project can be extended into an AI-powered tool by adding features such as:

- Automatically detecting the best compression quality
- Suggesting the best image size for web upload
- Identifying whether an image is a profile photo, product photo, or document image
- Applying different compression logic based on image type
- Removing unnecessary image background before compression
- Enhancing image quality after compression

---

## GitHub Commands

Initialize Git repository:

```bash
git init
```

Add files:

```bash
git add .
```

Commit changes:

```bash
git commit -m "Initial commit for ImageCompressor project"
```

Add remote repository:

```bash
git remote add origin https://github.com/aaron-creator/imageCompressor.git
```

Rename branch to main:

```bash
git branch -M main
```

Push to GitHub:

```bash
git push -u origin main
```

---

## Author

**Aaron Sam**

Java Full Stack Developer  
Spring Boot | Angular | Java | REST API

---

## License

This project is open-source and available for learning and portfolio purposes.

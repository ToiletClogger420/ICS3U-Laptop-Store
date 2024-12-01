# DCS Laptop Store

A Java-based desktop application that helps users find their ideal laptop through an interactive survey and recommendation system.

## 🌟 Features

- **Interactive Survey System**: Collects user preferences and requirements through a user-friendly interface
- **Smart Recommendation Engine**: Uses weighted cosine similarity to match user preferences with available laptops
- **Comprehensive Filtering Options**:
  - Price range
  - Brand preferences
  - Usage type (Student, Professional, Gaming, etc.)
  - Hardware specifications (CPU, GPU, RAM, Storage)
  - Operating system
  - Physical characteristics (Weight, Display size, Ports)
- **Dynamic Inventory Display**: Browse through available laptops with detailed specifications
- **Shopping Cart Integration**: Add preferred laptops to cart for purchase
- **Laptop Comparison**: View and compare multiple recommended options

## 🔧 Technical Architecture

The application is built using Java Swing for the GUI and follows an object-oriented design pattern. Key components include:

### Core Classes
- `LaptopStoreApplication.java`: Main application entry point
- `Laptop.java`: Data model for laptop objects
- `LaptopStoreInventoryFrame.java`: Handles the display of available laptops
- `LaptopStoreSurveyFrame.java`: Manages the user preference survey
- `LaptopStoreResultsFrame.java`: Processes and displays personalized recommendations
- `LaptopStoreTitleFrame.java`: Provides the main entry interface with survey and inventory options
- `LaptopStoreFileInput.java`: Handles CSV database file reading and laptop object initialization

### Key Features Implementation

#### Recommendation Algorithm
The application implements a sophisticated recommendation system using weighted cosine similarity to match user preferences with laptop characteristics. Key aspects include:

- **Feature Weighting**:
  - Price: 20%
  - Type, RAM, Storage, Rating: 10% each
  - Brand, CPU Brand, GPU Brand, Speed Rating, Ports, Display, Weight: 5% each

- **Hard Constraints**:
  - Operating System match
  - Touchscreen requirement match

- **Similarity Calculation**:
$$ Similarity = \frac{\sum_{i=1}^{n} w_i u_i l_i}{\sqrt{\sum_{i=1}^{n} w_i u_i^2} \sqrt{\sum_{i=1}^{n} w_i l_i^2}} $$

Where:
- $w_i$ is the weight of feature i
- $u_i$ is the normalized user preference for feature i
- $l_i$ is the normalized laptop value for feature i


- **Feature Normalization**:
  - Numerical values are normalized to [0,1] range
  - Categorical features use binary matching
  - Multi-value features use proportional matching

- **Performance Optimization**:
  - Results caching
  - Efficient feature comparison
  - Missing value handling

#### Other Key Features
- **Dynamic UI**: Responsive interface that adapts to user interactions
- **Data Management**: CSV-based data storage for laptop inventory
- **Image Handling**: Supports laptop image display and logo integration

## 🚀 Getting Started

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Java Runtime Environment (JRE)

### Directory Structure
```
project_root/
├── src/
│   ├── LaptopStoreApplication.java
│   ├── Laptop.java
│   ├── LaptopStoreInventoryFrame.java
│   ├── LaptopStoreSurveyFrame.java
│   ├── LaptopStoreResultsFrame.java
│   ├── LaptopStoreTitleFrame.java
│   └── LaptopStoreFileInput.java
├── data/
│   ├── database.csv
│   └── database_original.csv
├── images/
│   ├── logo.png
│   ├── logo_square.png
│   └── laptops/
├── lib/
│   ├── logback-classic-1.2.11.jar
│   ├── logback-core-1.2.11.jar
│   └── slf4j-api-1.7.36.jar
└── docs/
    └── RecommendationAlgorithmDocumentation.md
```

### Running the Application
1. Clone the repository
2. Ensure all required images and data files are in their respective directories
3. Compile the Java files
4. Run the application:
   ```bash
   java LaptopStoreApplication
   ```

## 🎯 Features In Detail

### Survey System
- **Price Range Selection**: Specify minimum and maximum budget
- **Brand Preferences**: Choose preferred laptop brands
- **Usage Type**: Select intended use case
- **Hardware Requirements**: Specify RAM, storage, and performance needs
- **Physical Preferences**: Set weight and size preferences

### Recommendation Engine
The system uses a sophisticated algorithm that considers:
- User preferences weight
- Hardware specifications
- Price constraints
- Usage requirements
- Physical characteristics

### Results Display
- Shows top 3 recommended laptops
- Displays key specifications
- Provides direct links to detailed information
- Allows immediate addition to shopping cart

## 👥 Contributors
- Derek Liang
- Shawn Lu
- Yucheng Chen

## 📝 License
This project is available under the MIT License. See the LICENSE file for more details.
# FlipGame - Client-Server Memory Match

A distributed, multi-player memory game built with **Java**. Players compete to flip and match identical word pairs in a grid. The project demonstrates advanced software engineering patterns, including proxy-based communication and real-time observer updates for competitive rankings.

## 🎮 Game Logic
- **Grid Mechanics**: Players tap two cells sequentially.
- **Matching**: If the words in the selected cells match, they remain visible.
- **Objective**: Match all pairs in the shortest time or fewest moves possible.

## 🏗 Architecture & Design Patterns

### 1. Client-Server Model
The game follows a centralized server architecture to ensure data integrity and synchronized game states across different clients.

### 2. Proxy Pattern
- **Communication Layer**: A Proxy is used to handle the communication between the Client and the Server.
- **Purpose**: It abstracts the networking details (sockets, serialization) from the UI/Business logic, acting as a local representation of the remote server.

### 3. Observer Pattern
- **Real-Time Rankings**: The project implements the Observer pattern to manage the leaderboard.
- **Dynamic Updates**: When a game finishes and scores change, the server notifies all subscribed "Observers" (connected clients) to update their local ranking views automatically without a manual refresh.

## 🛠 Tech Stack
- **Language**: Java
- **Networking**: Java Sockets (TCP/IP)
- **Patterns**: Proxy, Observer, Singleton (optional, for Server management)
- **Concurrency**: Multi-threading (to handle multiple simultaneous clients)

## 📋 Features
- **Centralized Game State**: Matching logic is validated server-side to prevent cheating.
- **Live Leaderboard**: High scores are pushed to all users via the Observer pattern.
- **Socket Communication**: Efficient byte-stream or object-stream data transfer.

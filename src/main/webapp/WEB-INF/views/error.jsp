
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Page Not Found - JobHub</title>
    <style>
     
       
    
       * {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

body {
    font-family: 'Arial', sans-serif;
    background: linear-gradient(135deg, #4A90E2 0%, #357ABD 100%);
    min-height: 100vh;
    display: flex;
    flex-direction: column;
}

/* Main Content */
.main-content {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 50px 20px;
    position: relative;
    overflow: hidden;
}

/* Background decorations */
.bg-decoration {
    position: absolute;
    opacity: 0.1;
    pointer-events: none;
}

.circle-1 {
    width: 200px;
    height: 200px;
    background: white;
    border-radius: 50%;
    top: 10%;
    right: 10%;
    animation: float 6s ease-in-out infinite;
}

.circle-2 {
    width: 150px;
    height: 150px;
    background: white;
    border-radius: 50%;
    bottom: 20%;
    left: 15%;
    animation: float 8s ease-in-out infinite reverse;
}

.circle-3 {
    width: 100px;
    height: 100px;
    background: white;
    border-radius: 50%;
    top: 60%;
    right: 30%;
    animation: float 7s ease-in-out infinite;
}

@keyframes float {
    0%, 100% { transform: translateY(0px); }
    50% { transform: translateY(-20px); }
}

/* Error Content */
.error-container {
    text-align: center;
    color: white;
    max-width: 600px;
    z-index: 10;
}

.error-code {
    font-size: 120px;
    font-weight: bold;
    margin-bottom: 20px;
    text-shadow: 0 4px 20px rgba(0, 0, 0, 0.2);
    animation: bounce 2s ease-in-out infinite;
}

@keyframes bounce {
    0%, 20%, 50%, 80%, 100% { transform: translateY(0); }
    40% { transform: translateY(-10px); }
    60% { transform: translateY(-5px); }
}

.error-title {
    font-size: 48px;
    font-weight: bold;
    margin-bottom: 20px;
    text-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
}

/* Job search mockup */
.mockup-container {
    position: absolute;
    right: 5%;
    top: 20%;
    opacity: 0.3;
    transform: rotate(10deg);
}

.search-mockup {
    background: rgba(255, 255, 255, 0.9);
    padding: 20px;
    border-radius: 15px;
    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
    width: 300px;
}

.search-bar {
    background: #f5f5f5;
    height: 40px;
    border-radius: 20px;
    margin-bottom: 15px;
    position: relative;
}

.search-bar::before {
    content: "🔍";
    position: absolute;
    left: 15px;
    top: 50%;
    transform: translateY(-50%);
}

.job-item {
    background: #f9f9f9;
    height: 30px;
    border-radius: 5px;
    margin-bottom: 10px;
}

.green-button {
    background: #27ae60;
    height: 35px;
    border-radius: 17px;
    width: 120px;
    margin-left: auto;
}

/* Responsive Design */
@media (max-width: 768px) {
    .error-code {
        font-size: 80px;
    }

    .error-title {
        font-size: 36px;
    }

    .mockup-container {
        display: none;
    }
}

 @keyframes ripple {
            to { transform: scale(4); opacity: 0; }
        }
    </style>
</head>
<body>

    <!-- Main Content -->
    <main class="main-content">
        <!-- Background Decorations -->
        <div class="bg-decoration circle-1"></div>
        <div class="bg-decoration circle-2"></div>
        <div class="bg-decoration circle-3"></div>

        <!-- Job Search Mockup -->
        <div class="mockup-container">
            <div class="search-mockup">
                <div class="search-bar"></div>
                <div class="job-item"></div>
                <div class="job-item"></div>
                <div class="job-item"></div>
                <div class="green-button"></div>
            </div>
        </div>

        <!-- Error Content -->
        <div class="error-container">
            <div class="error-code">404</div>
            <h1 class="error-title">Oops! Job Not Found</h1>

        </div>
    </main>

    <script>
        // Add some interactive behavior
        document.addEventListener('DOMContentLoaded', function() {
            // Animate error code on load
            const errorCode = document.querySelector('.error-code');
            errorCode.style.transform = 'scale(0)';
            errorCode.style.transition = 'transform 0.5s ease-out';
            
            setTimeout(() => {
                errorCode.style.transform = 'scale(1)';
            }, 200);

            // Add click effect to buttons
            const buttons = document.querySelectorAll('.btn-primary, .btn-secondary');
            buttons.forEach(button => {
                button.addEventListener('click', function(e) {
                    // Create ripple effect
                    const ripple = document.createElement('span');
                    ripple.style.cssText = `
                        position: absolute;
                        border-radius: 50%;
                        background: rgba(255,255,255,0.5);
                        transform: scale(0);
                        animation: ripple 0.6s linear;
                        pointer-events: none;
                    `;
                    
                    const rect = this.getBoundingClientRect();
                    const size = Math.max(rect.width, rect.height);
                    ripple.style.width = ripple.style.height = size + 'px';
                    ripple.style.left = e.clientX - rect.left - size / 2 + 'px';
                    ripple.style.top = e.clientY - rect.top - size / 2 + 'px';
                    
                    this.style.position = 'relative';
                    this.appendChild(ripple);
                    
                    setTimeout(() => ripple.remove(), 600);
                });
            });
        });
    </script>

   
</body>
</html>
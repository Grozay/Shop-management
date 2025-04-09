<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add New Product</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .container {
            max-width: 600px;
            margin-top: 50px;
        }
        .form-group {
            margin-bottom: 1rem;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2 class="text-center mb-4">Add New Product</h2>

        <form action="products" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="code" class="form-label">Product Code:</label>
                <input type="text" class="form-control" id="code" name="code"
                       placeholder="Enter product code" >
            </div>

            <div class="form-group">
                <label for="name" class="form-label">Product Name:</label>
                <input type="text" class="form-control" id="name" name="name"
                       placeholder="Enter product name" maxlength="256" >
            </div>

            <div class="form-group">
                <label for="price" class="form-label">Price:</label>
                <input type="text" class="form-control" id="price" name="price"
                       step="0.01" min="0" placeholder="Enter price" >
            </div>

            <div class="form-group">
                <label for="image" class="form-label">Product Image:</label>
                <input type="file" class="form-control" id="image" name="image"
                       accept="image/*" >
            </div>

            <div class="d-flex justify-content-between mt-4">
                <button type="submit" class="btn btn-primary">Add Product</button>
                <a href="/product.jsp" class="btn btn-secondary">Cancel</a>
            </div>
        </form>

        <!-- Hiển thị thông báo nếu có -->
        <%
            String message = (String) request.getAttribute("message");
            if (message != null) {
        %>
            <div class="alert alert-info mt-3" role="alert">
                <%= message %>
            </div>
        <% } %>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
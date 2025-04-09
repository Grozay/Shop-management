<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product List</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous">
</head>
<body>
    <div class="container mt-4">
        <h2 class="mb-4">Product List</h2>
        <a href="products?action=add" class="btn btn-primary my-3">Add New Product</a>

        <form action="products" method="get" class="d-flex my-3">
            <input type="hidden" name="action" value="search">
            <input type="text" name="keyword" class="form-control me-2" placeholder="Search...">
            <button type="submit" class="btn btn-secondary">Search</button>
        </form>
        <table class="table table-striped table-hover">
            <thead class="table-dark">
                <tr>
                    <th>Code</th>
                    <th>Name</th>
                    <th>Price</th>
                    <th>Image</th>
                    <th>Cart</th>
                    <th>Edit</th>
                    <th>Delete</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="product" items="${listProducts}">
                    <tr>
                        <td>${product.code}</td>
                        <td>${product.name}</td>
                        <td>$${product.price}</td>
                        <td>
                            <img src="${pageContext.request.contextPath}/${product.imagePath}"
                                 alt="${product.name}"
                                 class="img-thumbnail"
                                 style="max-width: 50px; max-height: 50px;">
                        </td>
                        <td>
                            <a href="products?action=addCart&code=${product.code}"
                               class="btn btn-sm btn-primary">
                                Add to Cart
                            </a>
                        </td>
                        <td>
                            <a href="products?action=update&code=${product.code}"
                               class="btn btn-sm btn-success">
                                update
                            </a>
                        </td>
                        <td>
                            <a href="products?action=delete&code=${product.code}"
                               class="btn btn-sm btn-danger">
                                Delete
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
            crossorigin="anonymous"></script>
</body>
</html>
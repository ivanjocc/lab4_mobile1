<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "lab4_mobile1";

// Crear conexión
$conn = new mysqli($servername, $username, $password, $dbname);

// Verificar conexión
if ($conn->connect_error) {
    die("Error: " . $conn->connect_error);
}

// Manejar la operación de inserción
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $name = $_POST["name"];
    $last_name = $_POST["last_name"];
    $usr = $_POST["usr"];
    $pwd = $_POST["pwd"];

    $stmt = $conn->prepare("INSERT INTO utilisateur (name, last_name, usr, pwd, dateEntry) VALUES (?, ?, ?, ?, NOW())");
    $stmt->bind_param("ssss", $name, $last_name, $usr, $pwd);

    if ($stmt->execute()) {
        echo "Good";
    } else {
        echo "Problem: " . $stmt->error;
    }

    $stmt->close();
}

// Cerrar conexión
$conn->close();
?>

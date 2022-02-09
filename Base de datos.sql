-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 10-02-2022 a las 00:09:22
-- Versión del servidor: 5.7.32-log
-- Versión de PHP: 7.4.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `minimarket`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria`
--

CREATE TABLE `categoria` (
  `codCategoria` int(11) NOT NULL,
  `nombre` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `categoria`
--

INSERT INTO `categoria` (`codCategoria`, `nombre`) VALUES
(1, 'Embutidos'),
(2, 'Licores'),
(3, 'Lácteos');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `codCliente` int(11) NOT NULL,
  `dni` varchar(20) NOT NULL,
  `nombres` varchar(200) NOT NULL,
  `apellidos` varchar(200) NOT NULL,
  `codTipoCliente` int(11) NOT NULL,
  `ruc` varchar(15) NOT NULL,
  `razonSocial` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`codCliente`, `dni`, `nombres`, `apellidos`, `codTipoCliente`, `ruc`, `razonSocial`) VALUES
(1, '71208489', 'Diego Ernesto', 'Vigo Briones', 1, '', ''),
(2, '', '', '', 2, '10164090588', 'Cedepas norte'),
(3, '26682687', 'Marycruz Rocio ', 'Briones Ordoñez', 1, '', ''),
(4, '26635079', 'Juan Julio', 'Briones Ordoñez', 1, '', ''),
(5, '17935624', 'Diego Ernesto', 'Maslucán Rojas', 1, '', ''),
(6, '', 'hola', 'apel', 1, '', ''),
(7, '73710888', 'Elisa Margarita', 'Maslucán Rojas', 1, '', ''),
(8, '70513890', 'Roxana Melissa', 'Diaz Valdez', 1, '', ''),
(9, '', '', '', 2, '12345678912', 'VIGO BRIONES DIEGO ERNESTO'),
(10, '26682688', 'Elsa Doris', 'Asencio Quiliche', 1, '', ''),
(11, '74387518', 'Estefany Maricielo', 'Rodriguez Paredes', 1, '', '');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_venta`
--

CREATE TABLE `detalle_venta` (
  `codDetalleVenta` int(11) NOT NULL,
  `codVenta` int(11) NOT NULL,
  `codProducto` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `precioUnitario` float NOT NULL,
  `total` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `detalle_venta`
--

INSERT INTO `detalle_venta` (`codDetalleVenta`, `codVenta`, `codProducto`, `cantidad`, `precioUnitario`, `total`) VALUES
(1, 1, 3, 52, 28.9, 1502.8),
(2, 2, 2, 6, 4.7, 28.2),
(3, 3, 4, 52, 12, 624),
(4, 4, 3, 52, 28.9, 1502.8),
(5, 4, 16, 1, 2, 2),
(6, 5, 4, 52, 12, 624),
(7, 6, 2, 3, 4.7, 14.1),
(8, 6, 9, 5, 25.9, 129.5),
(9, 7, 3, 2, 28.9, 57.8),
(10, 8, 3, 52, 28.9, 1502.8),
(11, 9, 4, 52, 12, 624),
(12, 10, 3, 122, 28.9, 3525.8),
(13, 11, 5, 52, 3.7, 192.4),
(14, 12, 4, 5, 12, 60),
(15, 13, 2, 51, 4.7, 239.7),
(16, 14, 6, 2, 39.99, 79.98),
(17, 15, 4, 48, 12, 576),
(18, 16, 5, 511, 3.7, 1890.7),
(19, 17, 2, 1521, 4.7, 7148.7),
(20, 17, 2, 5, 4.7, 23.5),
(21, 17, 16, 25, 2, 50),
(22, 18, 3, 15, 28.9, 433.5),
(23, 18, 3, 2, 28.9, 57.8),
(24, 19, 4, 52, 12, 624),
(25, 20, 3, 52, 28.9, 1502.8),
(26, 21, 2, 52, 4.7, 244.4),
(27, 22, 3, 52, 28.9, 1502.8),
(28, 23, 4, 4, 12, 48),
(29, 23, 10, 2, 6, 12),
(30, 24, 10, 11, 6, 66),
(31, 25, 10, 4, 6, 24),
(32, 25, 3, 15, 28.9, 433.5),
(33, 26, 7, 5, 29.99, 149.95),
(34, 26, 12, 2, 2.01, 4.02);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estado_producto`
--

CREATE TABLE `estado_producto` (
  `codEstadoProducto` int(11) NOT NULL,
  `nombre` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `estado_producto`
--

INSERT INTO `estado_producto` (`codEstadoProducto`, `nombre`) VALUES
(1, 'Habilitado'),
(2, 'Deshabilitado');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ingreso_almacen`
--

CREATE TABLE `ingreso_almacen` (
  `codIngresoAlmacen` int(11) NOT NULL,
  `codPersonalQueIngreso` int(11) NOT NULL,
  `fechaHoraIngreso` datetime NOT NULL,
  `costoTotal` float NOT NULL,
  `comentario` varchar(300) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `ingreso_almacen`
--

INSERT INTO `ingreso_almacen` (`codIngresoAlmacen`, `codPersonalQueIngreso`, `fechaHoraIngreso`, `costoTotal`, `comentario`) VALUES
(2, 3, '2022-01-11 00:00:00', 1474.25, 'primera'),
(46, 3, '2022-02-05 19:01:44', 204.25, 'Comentario reallll'),
(47, 3, '2022-02-09 14:43:41', 511, 'sda');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `lote`
--

CREATE TABLE `lote` (
  `codLote` int(11) NOT NULL,
  `codigoLegible` varchar(100) NOT NULL,
  `codProducto` int(11) NOT NULL,
  `codProveedor` int(11) NOT NULL,
  `codPunto` int(11) NOT NULL,
  `stock` int(11) NOT NULL,
  `fechaVencimiento` date NOT NULL,
  `stockIngresado` int(11) NOT NULL,
  `codIngresoAlmacen` int(11) NOT NULL,
  `costoCompraLote` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `lote`
--

INSERT INTO `lote` (`codLote`, `codigoLegible`, `codProducto`, `codProveedor`, `codPunto`, `stock`, `fechaVencimiento`, `stockIngresado`, `codIngresoAlmacen`, `costoCompraLote`) VALUES
(6, 'LOT0006-001', 6, 1, 1, 20, '2023-06-07', 20, 2, 420),
(7, 'LOT0007-001', 7, 1, 1, 15, '2023-05-17', 20, 2, 240),
(8, 'LOT0008-001', 8, 1, 1, 20, '2023-03-08', 20, 2, 40),
(9, 'LOT0009-001', 9, 1, 1, 20, '2022-12-19', 20, 2, 260),
(10, 'LOT0010-001', 10, 1, 1, 3, '2022-12-12', 20, 2, 60),
(11, 'LOT0011-001', 11, 1, 1, 20, '2023-04-12', 20, 2, 20),
(12, 'LOT0012-001', 12, 1, 1, 18, '2022-11-15', 20, 2, 14),
(13, 'LOT0013-001', 13, 1, 1, 20, '2022-11-16', 20, 2, 40),
(14, 'LOT0014-001', 14, 1, 1, 20, '2022-12-14', 20, 2, 100),
(15, 'LOT0015-001', 15, 1, 1, 20, '2022-09-20', 20, 2, 280),
(52, 'LOT0052-001', 3, 1, 1, 5, '2023-02-24', 20, 47, 511),
(55, 'LOT0055-001', 4, 1, 1, 14, '2022-02-05', 18, 46, 84),
(56, 'LOT0056-001', 4, 2, 1, 2, '2022-02-25', 2, 46, 120.25);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `parametro`
--

CREATE TABLE `parametro` (
  `codParametro` int(11) NOT NULL,
  `nombre` varchar(200) NOT NULL,
  `valor` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `personal`
--

CREATE TABLE `personal` (
  `codPersonal` int(11) NOT NULL,
  `nombres` varchar(200) NOT NULL,
  `apellidos` varchar(200) NOT NULL,
  `dni` char(8) NOT NULL,
  `codTipoPersonal` int(11) NOT NULL,
  `codUsuario` int(11) NOT NULL,
  `activo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `personal`
--

INSERT INTO `personal` (`codPersonal`, `nombres`, `apellidos`, `dni`, `codTipoPersonal`, `codUsuario`, `activo`) VALUES
(1, 'Admin admin', 'Admin Adm', '71208489', 0, 1, 1),
(2, 'Estefany Maricielo', 'Rodriguez Paredes', '71208489', 2, 2, 1),
(3, 'Eddie Renzo Junior', 'Franco Valladolid', '71208489', 3, 3, 1),
(4, 'Diego Ernesto', 'Vigo Briones', '71208489', 1, 4, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `codProducto` int(11) NOT NULL,
  `codCategoria` int(11) NOT NULL,
  `codEstadoProducto` int(11) NOT NULL,
  `nombre` varchar(200) NOT NULL,
  `codigoLegible` varchar(100) NOT NULL,
  `precioActual` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`codProducto`, `codCategoria`, `codEstadoProducto`, `nombre`, `codigoLegible`, `precioActual`) VALUES
(1, 1, 2, 'Chorizo ahumado BRAEDT empaque 500g', 'CHOA25', 29.9),
(2, 1, 1, 'Hot dog BRAEDT tradicional 200 g', 'PROD0002-001', 4.7),
(3, 1, 2, 'Chorizo Argentino BRAEDT empaque 500g', 'PROD0003-001', 28.9),
(4, 1, 1, 'Jamón de pavita BRAEDT paquete de 200 g', 'PROD0004-001', 12),
(5, 1, 1, 'Jamón pizzero BRAEDT paquete de 100 g', 'JAPIZ', 3.7),
(6, 2, 2, 'ANIS NAJAR azul semi seco botella 750ml', 'PROD0006-001', 39.99),
(7, 2, 1, 'Cerveza CORONA Extra 6 Pack Botella 355ml', 'PROD0007-001', 29.99),
(8, 2, 1, 'Cerveza CRISTAL botella 355ml', 'PROD0008-001', 4.9),
(9, 2, 2, 'Cerveza CUSQUEÑA sixpack en lata 355ml', 'PROD0009-001', 25.9),
(10, 2, 1, 'Cerveza HEINEKEN botella de 330ml', 'PROD0010-001', 6),
(11, 3, 1, 'Alimento lácteo GLORIA Yofresh durazno botella 320g', 'PROD0011-001', 3),
(12, 3, 1, 'Batidito vainilla PURA VIDA c/choco vaso de 90 g', 'PROD0012-001', 2.01),
(13, 3, 1, 'Bombones DONOFRIO 72ml', 'PROD0013-001', 4),
(14, 3, 1, 'Crema de leche NESTLE lata de 300 g', 'PROD0014-001', 8.5),
(15, 3, 1, 'El manjar NESTLE bolsa de 1kg', 'PROD0015-001', 21.12),
(16, 1, 1, 'Coca cola', 'EA25A', 2),
(17, 1, 2, 'Huamachuco', 'EA25AA', 251);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proveedor`
--

CREATE TABLE `proveedor` (
  `codProveedor` int(11) NOT NULL,
  `razonSocial` varchar(200) NOT NULL,
  `ruc` varchar(20) NOT NULL,
  `nombreContacto` varchar(200) NOT NULL,
  `telefonoContacto` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `proveedor`
--

INSERT INTO `proveedor` (`codProveedor`, `razonSocial`, `ruc`, `nombreContacto`, `telefonoContacto`) VALUES
(1, 'Las Torresx', '20100067910', 'Marcos Juan Velez Martinezx', '498432135x'),
(2, 'Juanita SAC', '11111111111', 'Marcos', '994443431'),
(3, 'CENTRO ECUMENICO DE PROMOCION Y ACCION SOCIAL NORTE', '10164090588', 'Marcos Juan Velez Martinez', '944435416854');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `punto_venta`
--

CREATE TABLE `punto_venta` (
  `codPunto` int(11) NOT NULL,
  `nombre` varchar(200) NOT NULL,
  `codPersonalCajero` int(11) NOT NULL,
  `direccion` varchar(200) NOT NULL,
  `activo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `punto_venta`
--

INSERT INTO `punto_venta` (`codPunto`, `nombre`, `codPersonalCajero`, `direccion`, `activo`) VALUES
(1, 'Minimarket Trujillo', 2, 'Condominios Parque de San Gabriel, Torre 8, Dpto 102', 1),
(2, 'Coca cola', 1, 'El Camino Real 251', 1),
(3, 'Avenida Manco Capac', 2, 'El Camino Real 251', 1),
(4, 'MiniYYY', 1, 'CYYYY', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipo_cdp`
--

CREATE TABLE `tipo_cdp` (
  `codTipoCDP` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `tipo_cdp`
--

INSERT INTO `tipo_cdp` (`codTipoCDP`, `nombre`) VALUES
(1, 'Boleta'),
(2, 'Factura');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipo_cliente`
--

CREATE TABLE `tipo_cliente` (
  `codTipoCliente` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `tipo_cliente`
--

INSERT INTO `tipo_cliente` (`codTipoCliente`, `nombre`) VALUES
(1, 'Persona Natural'),
(2, 'Persona Jurídica');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipo_personal`
--

CREATE TABLE `tipo_personal` (
  `codTipoPersonal` int(11) NOT NULL,
  `nombre` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `tipo_personal`
--

INSERT INTO `tipo_personal` (`codTipoPersonal`, `nombre`) VALUES
(0, 'AdminSistema'),
(1, 'Administrador'),
(2, 'Cajero'),
(3, 'Supervisor');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `codUsuario` int(11) NOT NULL,
  `usuario` varchar(200) NOT NULL,
  `password` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`codUsuario`, `usuario`, `password`) VALUES
(1, 'admin', '$2a$04$sX2DVw218lslrUcXa7FYAO37ZrgD5vEx2eQo/8tqnZ15rOFuRXwuS'),
(2, 'marsky', '$2a$04$QjmsViIKa2LiqhJnmKBzCuDFwnNMxp8ugBgRIkSeG0/jWttj9r2/q'),
(3, 'renzo', '$2a$04$X7jV8xO3R9fCFruuAAWhleJktW7AmOE2uqENYZ4T8HqawVwB0CmIC'),
(4, 'marsky', '$2a$04$rUNYcCsHhgbTFjhA3nf54.E4v6pdHZ4QOG4tD8ZDUCfPYm1Psib8a');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `venta`
--

CREATE TABLE `venta` (
  `codVenta` int(11) NOT NULL,
  `codigoLegible` varchar(100) NOT NULL,
  `codPunto` int(11) NOT NULL,
  `codPersonal` int(11) NOT NULL,
  `importeBruto` float NOT NULL,
  `igv` float NOT NULL,
  `importeTotal` float NOT NULL,
  `codCliente` int(11) NOT NULL,
  `fechaHora` datetime NOT NULL,
  `codTipoCDP` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `venta`
--

INSERT INTO `venta` (`codVenta`, `codigoLegible`, `codPunto`, `codPersonal`, `importeBruto`, `igv`, `importeTotal`, `codCliente`, `fechaHora`, `codTipoCDP`) VALUES
(3, 'V2022-000003', 1, 1, 528.814, 95.1865, 624, 1, '2022-02-08 14:06:40', 1),
(4, 'V2022-000004', 1, 1, 1275, 229.8, 1504.8, 1, '2022-02-08 14:09:14', 1),
(5, 'V2022-000005', 1, 1, 529, 95, 624, 1, '2022-02-08 17:10:06', 2),
(6, 'V2022-000006', 1, 1, 122, 21.6, 143.6, 1, '2022-02-08 17:46:47', 2),
(7, 'V2022-000007', 1, 1, 49, 8.8, 57.8, 2, '2022-02-08 17:47:36', 2),
(8, 'V2022-000008', 1, 1, 1274, 228.8, 1502.8, 1, '2022-02-08 18:05:04', 1),
(9, 'V2022-000009', 1, 1, 529, 95, 624, 1, '2022-02-08 18:30:17', 2),
(10, 'V2022-000010', 1, 1, 2988, 537.8, 3525.8, 1, '2022-02-08 18:37:29', 1),
(11, 'V2022-000011', 1, 1, 163, 29.4, 192.4, 1, '2022-02-08 18:39:58', 2),
(12, 'V2022-000012', 1, 1, 51, 9, 60, 1, '2022-02-08 18:41:25', 1),
(13, 'V2022-000013', 1, 1, 203, 36.7, 239.7, 2, '2022-02-08 19:32:25', 2),
(14, 'V2022-000014', 1, 1, 68, 11.98, 79.98, 8, '2022-02-08 19:33:52', 1),
(15, 'V2022-000015', 1, 1, 488, 88, 576, 8, '2022-02-08 19:35:37', 1),
(16, 'V2022-000016', 1, 1, 1602, 288.7, 1890.7, 9, '2022-02-08 19:37:45', 2),
(17, 'V2022-000017', 1, 1, 6121, 1101.2, 7222.2, 2, '2022-02-08 19:50:14', 2),
(18, 'V2022-000018', 1, 1, 416, 75.3, 491.3, 10, '2022-02-09 00:11:15', 1),
(19, 'V2022-000019', 1, 1, 529, 95, 624, 1, '2022-02-09 11:58:05', 2),
(20, 'V2022-000020', 3, 2, 1274, 228.8, 1502.8, 2, '2022-02-09 13:13:14', 2),
(21, 'V2022-000021', 3, 2, 207, 37.4, 244.4, 1, '2022-02-09 13:13:52', 2),
(22, 'V2022-000022', 3, 2, 1274, 228.8, 1502.8, 2, '2022-02-09 13:14:28', 2),
(23, 'V2022-000023', 1, 2, 51, 9, 60, 1, '2022-02-09 15:43:46', 1),
(24, 'V2022-000024', 1, 2, 56, 10, 66, 1, '2022-02-09 15:44:56', 1),
(25, 'V2022-000025', 1, 2, 388, 69.5, 457.5, 1, '2022-02-09 15:57:55', 1),
(26, 'V2022-000026', 1, 2, 130, 23.97, 153.97, 11, '2022-02-09 17:20:59', 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`codCategoria`);

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`codCliente`);

--
-- Indices de la tabla `detalle_venta`
--
ALTER TABLE `detalle_venta`
  ADD PRIMARY KEY (`codDetalleVenta`);

--
-- Indices de la tabla `estado_producto`
--
ALTER TABLE `estado_producto`
  ADD PRIMARY KEY (`codEstadoProducto`);

--
-- Indices de la tabla `ingreso_almacen`
--
ALTER TABLE `ingreso_almacen`
  ADD PRIMARY KEY (`codIngresoAlmacen`);

--
-- Indices de la tabla `lote`
--
ALTER TABLE `lote`
  ADD PRIMARY KEY (`codLote`);

--
-- Indices de la tabla `parametro`
--
ALTER TABLE `parametro`
  ADD PRIMARY KEY (`codParametro`);

--
-- Indices de la tabla `personal`
--
ALTER TABLE `personal`
  ADD PRIMARY KEY (`codPersonal`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`codProducto`);

--
-- Indices de la tabla `proveedor`
--
ALTER TABLE `proveedor`
  ADD PRIMARY KEY (`codProveedor`);

--
-- Indices de la tabla `punto_venta`
--
ALTER TABLE `punto_venta`
  ADD PRIMARY KEY (`codPunto`);

--
-- Indices de la tabla `tipo_cdp`
--
ALTER TABLE `tipo_cdp`
  ADD PRIMARY KEY (`codTipoCDP`);

--
-- Indices de la tabla `tipo_cliente`
--
ALTER TABLE `tipo_cliente`
  ADD PRIMARY KEY (`codTipoCliente`);

--
-- Indices de la tabla `tipo_personal`
--
ALTER TABLE `tipo_personal`
  ADD PRIMARY KEY (`codTipoPersonal`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`codUsuario`);

--
-- Indices de la tabla `venta`
--
ALTER TABLE `venta`
  ADD PRIMARY KEY (`codVenta`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categoria`
--
ALTER TABLE `categoria`
  MODIFY `codCategoria` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `cliente`
--
ALTER TABLE `cliente`
  MODIFY `codCliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `detalle_venta`
--
ALTER TABLE `detalle_venta`
  MODIFY `codDetalleVenta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- AUTO_INCREMENT de la tabla `estado_producto`
--
ALTER TABLE `estado_producto`
  MODIFY `codEstadoProducto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `ingreso_almacen`
--
ALTER TABLE `ingreso_almacen`
  MODIFY `codIngresoAlmacen` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=48;

--
-- AUTO_INCREMENT de la tabla `lote`
--
ALTER TABLE `lote`
  MODIFY `codLote` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=57;

--
-- AUTO_INCREMENT de la tabla `parametro`
--
ALTER TABLE `parametro`
  MODIFY `codParametro` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `personal`
--
ALTER TABLE `personal`
  MODIFY `codPersonal` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `producto`
--
ALTER TABLE `producto`
  MODIFY `codProducto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT de la tabla `proveedor`
--
ALTER TABLE `proveedor`
  MODIFY `codProveedor` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `punto_venta`
--
ALTER TABLE `punto_venta`
  MODIFY `codPunto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `tipo_cdp`
--
ALTER TABLE `tipo_cdp`
  MODIFY `codTipoCDP` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `tipo_cliente`
--
ALTER TABLE `tipo_cliente`
  MODIFY `codTipoCliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `tipo_personal`
--
ALTER TABLE `tipo_personal`
  MODIFY `codTipoPersonal` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `codUsuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `venta`
--
ALTER TABLE `venta`
  MODIFY `codVenta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

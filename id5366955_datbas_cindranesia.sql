-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Waktu pembuatan: 30 Jun 2018 pada 11.42
-- Versi server: 10.1.31-MariaDB
-- Versi PHP: 7.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `id5366955_datbas_cindranesia`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `detail_pemesanan`
--

CREATE TABLE `detail_pemesanan` (
  `id_detail` int(11) NOT NULL,
  `id_pemesanan` int(11) NOT NULL,
  `jumlah_item` int(10) NOT NULL,
  `total_harga` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktur dari tabel `favorit`
--

CREATE TABLE `favorit` (
  `id_favorit` int(11) NOT NULL,
  `id_toko` int(11) NOT NULL,
  `id_produk` int(11) NOT NULL,
  `id_user` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `favorit`
--

INSERT INTO `favorit` (`id_favorit`, `id_toko`, `id_produk`, `id_user`) VALUES
(12, 1, 1, 2),
(4, 1, 1, 22),
(10, 1, 1, 31),
(11, 1, 1, 33),
(14, 2, 3, 2),
(5, 3, 5, 22),
(6, 3, 5, 22),
(7, 3, 5, 22),
(8, 3, 5, 22),
(13, 3, 6, 2);

-- --------------------------------------------------------

--
-- Struktur dari tabel `keranjang`
--

CREATE TABLE `keranjang` (
  `id_keranjang` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `id_toko` int(11) NOT NULL,
  `id_produk` int(11) NOT NULL,
  `jml_pesan` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `keranjang`
--

INSERT INTO `keranjang` (`id_keranjang`, `id_user`, `id_toko`, `id_produk`, `jml_pesan`) VALUES
(6, 2, 1, 1, 2),
(7, 22, 3, 5, 1),
(8, 22, 2, 4, 2),
(9, 22, 3, 6, 5),
(11, 2, 2, 3, 1),
(12, 2, 2, 3, 2),
(15, 31, 1, 1, 1),
(16, 2, 1, 1, 2),
(17, 2, 3, 6, 4),
(18, 2, 2, 3, 6),
(19, 2, 2, 3, 2),
(20, 2, 2, 3, 23);

-- --------------------------------------------------------

--
-- Struktur dari tabel `pemesanan`
--

CREATE TABLE `pemesanan` (
  `id_pemesanan` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `id_produk` int(11) NOT NULL,
  `id_toko` int(11) NOT NULL,
  `jumlah_pesan` varchar(11) NOT NULL,
  `total_harga` varchar(11) NOT NULL,
  `tgl_pesan` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `pemesanan`
--

INSERT INTO `pemesanan` (`id_pemesanan`, `id_user`, `id_produk`, `id_toko`, `jumlah_pesan`, `total_harga`, `tgl_pesan`) VALUES
(5, 2, 1, 1, '2', '15000', '2018-04-22');

-- --------------------------------------------------------

--
-- Struktur dari tabel `produk`
--

CREATE TABLE `produk` (
  `id_produk` int(11) NOT NULL,
  `id_toko` int(11) NOT NULL,
  `jenis_produk` enum('Makanan','Barang','Minuman') NOT NULL,
  `judul_produk` varchar(100) NOT NULL,
  `harga_produk` varchar(10) NOT NULL,
  `deskripsi_produk` text NOT NULL,
  `path_produk` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `produk`
--

INSERT INTO `produk` (`id_produk`, `id_toko`, `jenis_produk`, `judul_produk`, `harga_produk`, `deskripsi_produk`, `path_produk`) VALUES
(1, 1, 'Makanan', 'Bandrek', '12000', 'blablabla', 'https://cindranesia.000webhostapp.com/images/produk/1002.png'),
(3, 2, 'Makanan', 'Bakpia Kumbu Hitam', '20000', 'Bakpia kumbu hitam menggunakan bahan dari kedelai pilihan. \r\nKami olah dengan standard kualitas produksi untuk menjaga rasa terbaik.', 'https://cindranesia.000webhostapp.com/images/produk/1001.png'),
(4, 2, 'Makanan', 'Bakpia Coklat', '25000', 'Bakpia 145 Ibu Sri Astuti menyediakan bakpia coklat yang dibuat dari bahan coklat berkualitas.', 'https://cindranesia.000webhostapp.com/images/produk/1000.png'),
(5, 3, 'Barang', 'Kebaya Anak Lengan Pendek', '67500', 'Menggunakan kualitas bahan bagus, menggunakan bahan Katun Primisima. Warna yang tersedia: biru, merah, pink, dan ungu. Ukuran s, m, l, xl', 'https://cindranesia.000webhostapp.com/images/produk/1003.png'),
(6, 3, 'Barang', 'Selendang Bumi Jaya Mahkota 02', '55500', 'Kami menggunakan kualitas bahan bagus, menggunakan bahan Katun Primissima. Panjang: 265 cm, lebar: 90cm', 'https://cindranesia.000webhostapp.com/images/produk/1004.png');

-- --------------------------------------------------------

--
-- Struktur dari tabel `riwayat_pesan`
--

CREATE TABLE `riwayat_pesan` (
  `id_riwayat` int(11) NOT NULL,
  `id_pemesanan` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktur dari tabel `toko`
--

CREATE TABLE `toko` (
  `id_toko` int(11) NOT NULL,
  `nama_toko` varchar(50) NOT NULL,
  `alamat_toko` varchar(100) NOT NULL,
  `kota_toko` varchar(30) NOT NULL,
  `no_surat` varchar(50) NOT NULL,
  `path_surat` varchar(250) NOT NULL,
  `arah` varchar(100) NOT NULL,
  `id_user` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `toko`
--

INSERT INTO `toko` (`id_toko`, `nama_toko`, `alamat_toko`, `kota_toko`, `no_surat`, `path_surat`, `arah`, `id_user`) VALUES
(1, 'iteung', 'jalan tangkuban perahu', 'Bandung', '', '', 'https://goo.gl/maps/69dpngUQ1hs', 1),
(2, 'Bakpia 145 Ibu Sri Astuti', 'Jl. Kusumanegara No.151, Muja Muju, Umbulharjo', 'Yogyakarta', '', '', 'https://goo.gl/maps/ECX992RgJdm', 7),
(3, 'Batik Unggul Jaya', 'Pasar Banjarsari 1st Floor Blok D No. 25-27, JL. Sultan Agung, Pekalongan, Sampangan', 'Pekalongan', '', '', 'https://goo.gl/maps/NkoXisc3zkG2', 8),
(20, 'Esti toko', 'jalan bekasi', 'bekasi', '089551465', 'https://cindranesia.000webhostapp.com/images/surat/3.png', 'http://hsbsgajdbahan', 34);

-- --------------------------------------------------------

--
-- Struktur dari tabel `ulasan`
--

CREATE TABLE `ulasan` (
  `id_ulasan` int(11) NOT NULL,
  `id_produk` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `ulasan` text NOT NULL,
  `tgl_ulasan` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `ulasan`
--

INSERT INTO `ulasan` (`id_ulasan`, `id_produk`, `id_user`, `ulasan`, `tgl_ulasan`) VALUES
(10, 1, 22, 'Enak nih,  anget', '2018-04-21'),
(11, 1, 22, 'Patut dicoba nih', '2018-04-21'),
(12, 6, 22, 'Uhhh bgusss', '2018-04-21'),
(13, 6, 22, 'Hahaha', '2018-04-21'),
(15, 1, 2, 'Enak banget', '2018-04-21'),
(16, 4, 31, 'baik', '2018-04-22'),
(17, 1, 2, 'enak', '2018-04-22');

-- --------------------------------------------------------

--
-- Struktur dari tabel `user`
--

CREATE TABLE `user` (
  `id_user` int(11) NOT NULL,
  `nama_lengkap` varchar(50) NOT NULL,
  `username` varchar(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(32) NOT NULL,
  `no_telp` varchar(13) NOT NULL,
  `tempat_lahir` varchar(30) NOT NULL,
  `tgl_lahir` date NOT NULL,
  `jenis_kelamin` varchar(13) NOT NULL,
  `alamat` varchar(100) NOT NULL,
  `path_profil` varchar(250) NOT NULL,
  `role` enum('user','owner') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `user`
--

INSERT INTO `user` (`id_user`, `nama_lengkap`, `username`, `email`, `password`, `no_telp`, `tempat_lahir`, `tgl_lahir`, `jenis_kelamin`, `alamat`, `path_profil`, `role`) VALUES
(1, 'Harun', 'harun', 'harun@gmail.com', '6f1faa616614c07e6adb238a329f0541', '8961516', 'Yogyakarta', '1997-04-04', 'Laki-Laki', 'Depok', 'https://cindranesia.000webhostapp.com/images/profil/24.png', 'owner'),
(2, 'Esti Rosmana', 'esti', 'estiros@gmail.com', 'd7803d1b4f6d7aa71cb43cb13e924709', '087782900503', 'Bekasi', '1997-03-26', 'Perempuan', 'Bekasi', 'https://cindranesia.000webhostapp.com/images/profil/24.png', 'user'),
(7, 'wkwk', 'wkwk', 'wkwk@gmail.com', 'da021b9a9c11f58f05bf9d22adc20083', '08961672278', '', '0000-00-00', '', '', '', 'owner'),
(8, 'kokoko', 'koko', 'koko@gmail.com', '748506ac7ea3251053cd61e909f05f96', '0897678976', '', '0000-00-00', '', '', '', 'owner'),
(12, 'sebastian fetel', 'sebastian', 'sebastianfetel@gmail', '95d85cee6eef2602e28554b2fb3a2d95', '098789098767', '', '0000-00-00', '', '', '', 'user'),
(13, 'asal', 'asal', 'asal@gmail.com', 'a5ad6b4624c65be1cd230a9b04d80399', '09978909876', '', '0000-00-00', '', '', '', 'owner'),
(14, 'test', 'test', 'test@gmail.com', 'cc03e747a6afbbcbf8be7668acfebee5', '089767898765', '', '0000-00-00', '', '', '', 'owner'),
(15, 'testlagi', 'testlagi', 'testlagi@gmail.com', 'eb2ab27056675bd80a398f03be47d56c', '089767898', '', '0000-00-00', '', '', '', 'owner'),
(16, 'cobaterus', 'cobaterus', 'cobaterus@gmail.com', '168015168adf6ab86b70eac06f3c9b6d', '098789098767', '', '0000-00-00', '', '', '', 'owner'),
(17, 'cobaaaa4', 'coba4', 'coba4@gmail.com', '6e7b9ec43d9c354093301f7b5f1fe103', '098789876787', '', '0000-00-00', '', '', '', 'user'),
(18, 'cobaa5', 'coba5', 'coba5@gmail.com', '0872d9c38b607c695eb7101821ccf0b0', '098789876789', '', '0000-00-00', '', '', '', 'owner'),
(19, 'Harun', 'cobaa7', 'dnsjdn@gmail.com', '4ba880a19779910ac86384008c743603', '3728372', '', '0000-00-00', '', '', 'https://cindranesia.000webhostapp.com/images/profi', 'user'),
(20, 'ndsjndsaj', 'coba8', 'sndja@gmail.com', 'bbf20eef8f6a69c86be064b7186d4e07', '3283728', '', '0000-00-00', '', 'Depok', 'https://cindranesia.000webhostapp.com/images/profil/24.png', 'owner'),
(22, 'Mutia Safari', 'mute', 'mutiasafari97@gmail.', 'bc52a12b76949c90c464336a264cc5da', '087880260774', 'Sukoharjo', '1997-07-12', 'Perempuan', 'Cibinong', 'https://cindranesia.000webhostapp.com/images/profil/24.png', 'user'),
(23, 'testdulu', 'testdulu', 'testdulu@gmail.com', '124a6e284490eaf5e70a69fa3389d053', '0764927181', 'Bogor', '0000-00-00', '', '', 'https://cindranesia.000webhostapp.com/images/profil/24.png', 'user'),
(24, 'cobaa10', 'coba10', 'cobaa@gmail.com', 'a97bbdd238480c04f7eb8e38f1d6addd', '829839', '', '0000-00-00', '', '', '', 'owner'),
(30, 'Untung Sujarwo', '', 'untung@gt.com', '5c02c4a580de948eb096baa8d324e345', '082345677888', '', '0000-00-00', '', '', '', 'user'),
(31, 'ganjar', 'ganjar', 'ganjar@ganjar.com', '25d55ad283aa400af464c76d713c07ad', '0283748485', '', '0000-00-00', '', '', '', 'user'),
(32, 'Untung Sujarwo', '', 'untung@gt.com', '5c02c4a580de948eb096baa8d324e345', '081213556789', '', '0000-00-00', '', '', '', 'user'),
(33, 'Untung Sujarwo', 'untungsujarwow', 'untung@mail.com', '5c02c4a580de948eb096baa8d324e345', '081215770706', '', '0000-00-00', '', '', '', 'user'),
(34, 'esti rosmana', 'esti', 'esti@gmail.com', '3034b52b8273b81413786c1737a78a87', '089645369858', '', '0000-00-00', '', '', '', 'owner'),
(35, 'marsekal rama', 'ramaaja1997', 'marsekal.rama@mail.ugm.ac.id', '81dc9bdb52d04dc20036dbd8313ed055', '08987383314', '', '0000-00-00', '', '', '', 'user'),
(36, 'hanifa aulia', 'hahanifaa', 'hanifalia91@gmail.com', '827ccb0eea8a706c4c34a16891f84e7b', '0895706586610', '', '0000-00-00', '', '', '', 'user'),
(37, '', '', '', 'd41d8cd98f00b204e9800998ecf8427e', '', '', '0000-00-00', '', '', '', 'owner'),
(38, '', '', '', 'd41d8cd98f00b204e9800998ecf8427e', '', '', '0000-00-00', '', '', '', 'user'),
(39, '', '', '', 'd41d8cd98f00b204e9800998ecf8427e', '', '', '0000-00-00', '', '', '', 'owner'),
(40, '', '', '', 'd41d8cd98f00b204e9800998ecf8427e', '', '', '0000-00-00', '', '', '', 'user'),
(41, '', '', '', 'd41d8cd98f00b204e9800998ecf8427e', '', '', '0000-00-00', '', '', '', 'owner'),
(42, '', '', '', 'd41d8cd98f00b204e9800998ecf8427e', '', '', '0000-00-00', '', '', '', 'owner'),
(43, '', '', '', 'd41d8cd98f00b204e9800998ecf8427e', '', '', '0000-00-00', '', '', '', 'user'),
(44, '', '', '', 'd41d8cd98f00b204e9800998ecf8427e', '', '', '0000-00-00', '', '', '', 'owner'),
(45, '', '', '', 'd41d8cd98f00b204e9800998ecf8427e', '', '', '0000-00-00', '', '', '', 'owner'),
(46, '', '', '', 'd41d8cd98f00b204e9800998ecf8427e', '', '', '0000-00-00', '', '', '', 'owner'),
(47, '', '', '', 'd41d8cd98f00b204e9800998ecf8427e', '', '', '0000-00-00', '', '', '', 'owner'),
(48, '', '', '', 'd41d8cd98f00b204e9800998ecf8427e', '', '', '0000-00-00', '', '', '', 'user'),
(49, '', '', '', 'd41d8cd98f00b204e9800998ecf8427e', '', '', '0000-00-00', '', '', '', 'owner'),
(50, '', '', '', 'd41d8cd98f00b204e9800998ecf8427e', '', '', '0000-00-00', '', '', '', 'user'),
(51, '', '', '', 'd41d8cd98f00b204e9800998ecf8427e', '', '', '0000-00-00', '', '', '', 'owner');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `detail_pemesanan`
--
ALTER TABLE `detail_pemesanan`
  ADD PRIMARY KEY (`id_detail`),
  ADD KEY `id_pemesanan` (`id_pemesanan`);

--
-- Indeks untuk tabel `favorit`
--
ALTER TABLE `favorit`
  ADD PRIMARY KEY (`id_favorit`),
  ADD KEY `id_toko` (`id_toko`,`id_produk`,`id_user`),
  ADD KEY `id_produk` (`id_produk`),
  ADD KEY `id_user` (`id_user`);

--
-- Indeks untuk tabel `keranjang`
--
ALTER TABLE `keranjang`
  ADD PRIMARY KEY (`id_keranjang`),
  ADD KEY `id_user` (`id_user`,`id_toko`,`id_produk`),
  ADD KEY `id_produk` (`id_produk`),
  ADD KEY `id_toko` (`id_toko`);

--
-- Indeks untuk tabel `pemesanan`
--
ALTER TABLE `pemesanan`
  ADD PRIMARY KEY (`id_pemesanan`),
  ADD KEY `id_user` (`id_user`),
  ADD KEY `id_produk` (`id_produk`),
  ADD KEY `id_toko` (`id_toko`);

--
-- Indeks untuk tabel `produk`
--
ALTER TABLE `produk`
  ADD PRIMARY KEY (`id_produk`),
  ADD KEY `id_toko` (`id_toko`);

--
-- Indeks untuk tabel `riwayat_pesan`
--
ALTER TABLE `riwayat_pesan`
  ADD PRIMARY KEY (`id_riwayat`),
  ADD KEY `id_pemesanan` (`id_pemesanan`);

--
-- Indeks untuk tabel `toko`
--
ALTER TABLE `toko`
  ADD PRIMARY KEY (`id_toko`),
  ADD KEY `id_owner` (`id_user`),
  ADD KEY `id_user` (`id_user`);

--
-- Indeks untuk tabel `ulasan`
--
ALTER TABLE `ulasan`
  ADD PRIMARY KEY (`id_ulasan`),
  ADD KEY `id_produk` (`id_produk`,`id_user`),
  ADD KEY `id_user` (`id_user`);

--
-- Indeks untuk tabel `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `detail_pemesanan`
--
ALTER TABLE `detail_pemesanan`
  MODIFY `id_detail` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT untuk tabel `favorit`
--
ALTER TABLE `favorit`
  MODIFY `id_favorit` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT untuk tabel `keranjang`
--
ALTER TABLE `keranjang`
  MODIFY `id_keranjang` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT untuk tabel `pemesanan`
--
ALTER TABLE `pemesanan`
  MODIFY `id_pemesanan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT untuk tabel `produk`
--
ALTER TABLE `produk`
  MODIFY `id_produk` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT untuk tabel `riwayat_pesan`
--
ALTER TABLE `riwayat_pesan`
  MODIFY `id_riwayat` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT untuk tabel `toko`
--
ALTER TABLE `toko`
  MODIFY `id_toko` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT untuk tabel `ulasan`
--
ALTER TABLE `ulasan`
  MODIFY `id_ulasan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT untuk tabel `user`
--
ALTER TABLE `user`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=52;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `detail_pemesanan`
--
ALTER TABLE `detail_pemesanan`
  ADD CONSTRAINT `detail_pemesanan_ibfk_1` FOREIGN KEY (`id_pemesanan`) REFERENCES `pemesanan` (`id_pemesanan`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `favorit`
--
ALTER TABLE `favorit`
  ADD CONSTRAINT `favorit_ibfk_1` FOREIGN KEY (`id_produk`) REFERENCES `produk` (`id_produk`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `favorit_ibfk_2` FOREIGN KEY (`id_toko`) REFERENCES `toko` (`id_toko`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `favorit_ibfk_3` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `keranjang`
--
ALTER TABLE `keranjang`
  ADD CONSTRAINT `keranjang_ibfk_2` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `keranjang_ibfk_3` FOREIGN KEY (`id_produk`) REFERENCES `produk` (`id_produk`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `keranjang_ibfk_4` FOREIGN KEY (`id_toko`) REFERENCES `toko` (`id_toko`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `pemesanan`
--
ALTER TABLE `pemesanan`
  ADD CONSTRAINT `pemesanan_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `pemesanan_ibfk_2` FOREIGN KEY (`id_produk`) REFERENCES `produk` (`id_produk`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `pemesanan_ibfk_3` FOREIGN KEY (`id_toko`) REFERENCES `toko` (`id_toko`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `produk`
--
ALTER TABLE `produk`
  ADD CONSTRAINT `produk_ibfk_1` FOREIGN KEY (`id_toko`) REFERENCES `toko` (`id_toko`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `riwayat_pesan`
--
ALTER TABLE `riwayat_pesan`
  ADD CONSTRAINT `riwayat_pesan_ibfk_1` FOREIGN KEY (`id_pemesanan`) REFERENCES `pemesanan` (`id_pemesanan`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `toko`
--
ALTER TABLE `toko`
  ADD CONSTRAINT `toko_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `ulasan`
--
ALTER TABLE `ulasan`
  ADD CONSTRAINT `ulasan_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `ulasan_ibfk_2` FOREIGN KEY (`id_produk`) REFERENCES `produk` (`id_produk`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

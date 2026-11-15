# Nangor Blast

Game Bomberman berbasis CLI (Java) untuk Tugas Besar IF2010 Pemrograman Berorientasi Objek 2026/2027.
**Kelompok D** - map **White Glacier** (boss Mantis, Ice Tile).

## Kebutuhan
- JDK 17 atau lebih baru
- Apache Maven 3.9+
- GNU Make

## Kompilasi dan menjalankan
```bash
make build     # compile
make test      # unit test
make run       # build jar lalu jalankan game
make clean     # hapus hasil build
```
Alternatif tanpa Make: `mvn package && java -jar target/nangor-blast.jar`.

## Struktur repo
| Path | Isi |
|------|-----|
| `src/main/java/nangorblast` | Kode program (core, model, controller, view, ...) |
| `src/test/java` | Unit test |
| `config/maps` | Layout map yang dimuat program |
| `doc` | Dokumen PDF (buklet, class diagram, log activity) |
| `spec` | Spesifikasi tugas besar |

## Pembagian tugas
| Anggota | NIM | Tugas |
|---------|-----|-------|
| TBD | TBD | TBD |

## Status fitur
| Kode | Fitur | Status |
|------|-------|--------|
| F00 | Inisialisasi & validasi map | Belum |
| F01 | Player | Belum |
| F02 | Bomb (Normal, Pumped, Remote) | Belum |
| F03 | Explosion | Belum |
| F04 | Block | Belum |
| F05 | Enemy & Boss Mantis | Belum |
| F06 | Power-Up | Belum |
| F07 | Map White Glacier (Ice Tile) | Belum |
| F08 | Score | Belum |
| F09 | Game flow (menu, pause, win/lose) | Belum |
| B01-B05 | Bonus | Belum dipilih |

Alur kerja Git ada di [CONTRIBUTING.md](CONTRIBUTING.md).

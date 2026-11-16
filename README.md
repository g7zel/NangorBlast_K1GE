# Nangor Blast

Game Bomberman berbasis CLI (Java) untuk Tugas Besar IF2010 Pemrograman Berorientasi Objek 2026/2027.
**Kelompok E** - map **White Glacier** (boss Mantis, Ice Tile).

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
Urut berdasarkan NIM.

| Anggota | NIM | Fitur | Package / class utama | Branch |
|---------|-----|-------|-----------------------|--------|
| Jason Samuel | 18223091 | F05 Enemy | `model.enemy`, `model.boss`, `model.boss.attack` | `feature/f05-mantis` |
| Tristan Anthony Wijaya | 18225045 | F00 Inisialisasi, F06 Power-Up, F07 Map | `config`, `exception`, `model.map`, `model.powerup` | `feature/f00-map-init`, `feature/f06-powerup`, `feature/f07-map-ice` |
| Fayza Kamilia | 18225097 | F01 Player, F08 Score, F09 Game Flow | `model.entity`, `score`, `core`, `controller`, `view`, `util`, `Main`; setup proyek dan koordinasi integrasi | `chore/shared-types`, `feature/f01-player`, `feature/f08-score`, `feature/f09-game-flow` |
| Omerio Orlando Manalu | 18225117 | F02 Bomb, F03 Explosion, F04 Block | `model.bomb`, `model.block`, implementasi `BombHandler` | `feature/f02-bomb` (F03, F04 menyusul) |

## Cara kolaborasi
- Branch: `main` (rilis), `develop` (integrasi), `feature/*` (satu per fitur, dari `develop`).
- Tidak ada push langsung ke `main` atau `develop`; semua lewat pull request dengan minimal satu reviewer dan CI hijau, lalu squash merge.
- Pesan commit berawalan `feat:`, `fix:`, `chore:`, `test:`, atau `docs:`.
- Urutan merge: `chore/shared-types` dulu, lalu `f00` dan `f08`, lalu fitur lain, dan `f09-game-flow` terakhir.
- Kontrak antar-class disepakati lebih dulu; perubahan class bersama (`Position`, `Direction`, `Entity`, `GameEngine`) dibuat dalam PR tersendiri.
- Setiap fitur disertai unit test JUnit; jalankan `make build` dan `make test` sebelum membuka PR.

## Jadwal
| Tanggal | Kegiatan |
|---------|----------|
| 16 Nov 2026, 20.10 WIB | Asistensi 1 |
| 17 Nov 2026, 20.10 WIB | Milestone 1: tautan repo, PDF pembagian tugas, PDF class diagram |
| 3 Des 2026 | Asistensi 2 |
| 4 Des 2026 | Milestone 2: release (source, buklet, log activity) |

## Status fitur
| Kode | Fitur | Penanggung jawab | Status |
|------|-------|------------------|--------|
| F00 | Inisialisasi & validasi map | Tristan | Belum |
| F01 | Player | Fayza | Implementasi awal + unit test (belum di-merge) |
| F02 | Bomb (Normal, Pumped, Remote) | Omerio | Belum |
| F03 | Explosion | Omerio | Belum |
| F04 | Block | Omerio | Belum |
| F05 | Enemy & Boss Mantis | Jason | Belum |
| F06 | Power-Up | Tristan | Belum |
| F07 | Map White Glacier (Ice Tile) | Tristan | Belum |
| F08 | Score | Fayza | Implementasi awal + unit test (belum di-merge) |
| F09 | Game flow (menu, pause, win/lose) | Fayza | Implementasi awal + unit test (belum di-merge) |
| B01-B05 | Bonus | - | Belum dipilih |

Alur kerja Git ada di [CONTRIBUTING.md](CONTRIBUTING.md).

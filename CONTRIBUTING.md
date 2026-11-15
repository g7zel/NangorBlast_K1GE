# Panduan Kontribusi

## Branch
- `main`: stabil, hanya menerima merge dari `develop` saat milestone. Beri tag (`milestone-1`, `milestone-2`).
- `develop`: integrasi. Semua fitur masuk lewat Pull Request ke sini.
- `feature/<kode>-<nama>`: satu fitur per branch, dibuat dari `develop`. Contoh: `feature/f05-mantis`.
- `fix/<nama>`: perbaikan bug.

Jangan push langsung ke `main` atau `develop`.

## Alur
1. `git switch develop && git pull`
2. `git switch -c feature/f02-bomb`
3. Commit kecil dan sering, lalu `git push -u origin feature/f02-bomb`
4. Buka PR ke `develop`, minta minimal 1 review anggota lain.
5. Merge setelah CI hijau dan review disetujui. Pakai squash merge agar mudah di-revert.

## Format commit
`<tipe>: <ringkasan>`, tipe: `feat`, `fix`, `refactor`, `test`, `docs`, `chore`.
Contoh: `feat: tambah PumpedBomb`.

## Aturan
- Satu orang satu area package untuk menghindari konflik; koordinasikan sebelum mengubah class milik orang lain.
- Pastikan `make build` dan `make test` lolos sebelum membuka PR.
- Semua kode harus bisa dijelaskan pemilik PR saat asistensi/demo.

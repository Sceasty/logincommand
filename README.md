# LoginCommand Plugin

**Geliştirici:** sceasty

Oyuncular sunucuya girdiğinde otomatik olarak komutlar çalıştıran Minecraft plugin'i.

## Özellikler

- ✅ Oyuncu girişinde otomatik komut çalıştırma
- ✅ **İlk giriş komutları** - Oyuncu ilk defa girdiğinde özel komutlar
- ✅ Console veya oyuncu komutları
- ✅ Ayarlanabilir gecikme sistemi
- ✅ Sınırsız komut ekleme/silme
- ✅ Placeholder desteği ({player}, {uuid})
- ✅ Oyun içi yönetim komutları
- ✅ Çoklu dil desteği (İngilizce/Türkçe)
- ✅ Oyuncu verilerini sıfırlama özelliği

## Kurulum

1. Plugin'i `plugins` klasörüne atın
2. Sunucuyu başlatın
3. `config.yml` dosyasını düzenleyin
4. `/lc reload` komutu ile config'i yenileyin

## Komutlar

### Ana Komut: `/logincommand` veya `/lc`

**Normal Komutlar (Her girişte çalışır):**
- `/lc add <komut> [console/player] [delay]` - Yeni komut ekle
- `/lc remove <komut>` - Komut sil  
- `/lc list` - Komutları listele

**İlk Giriş Komutları (Sadece ilk girişte çalışır):**
- `/lc addfirst <komut> [console/player] [delay]` - İlk giriş komutu ekle
- `/lc removefirst <komut>` - İlk giriş komutu sil
- `/lc listfirst` - İlk giriş komutlarını listele

**Diğer Komutlar:**
- `/lc toggle [commands/firstcommands]` - Özellikleri aç/kapat
- `/lc reload` - Config ve mesajları yeniden yükle
- `/lc resetplayers` - Oyuncu verilerini sıfırla (herkes tekrar ilk defa giriyor sayılır)

### Örnekler

**Normal komutlar:**
```
/lc add "say {player} hoşgeldin!" console 3
/lc add "spawn" player 1
/lc add "give {player} diamond 5" console 0
```

**İlk giriş komutları:**
```
/lc addfirst "say {player} ilk defa hoşgeldin!" console 1
/lc addfirst "give {player} diamond 10" console 3
/lc addfirst "tp {player} tutorial" console 5
```

**Komut silme:**
```
/lc remove "say {player} hoşgeldin!"
/lc removefirst "give {player} diamond 10"
```

## Config Yapısı

```yaml
# Ayarlar
settings:
  commands-enabled: true          # Normal komutlar (varsayılan: açık)
  first-commands-enabled: false   # İlk giriş komutları (varsayılan: kapalı)
  language: "tr"                  # tr = Türkçe, en = İngilizce

# Normal komutlar - Her girişte çalışır
commands:
  komut_id:
    command: "çalıştırılacak komut"
    console: true/false  # true = console, false = player
    delay: 5  # saniye cinsinden gecikme

# İlk giriş komutları - Sadece ilk girişte çalışır
first-time-commands:
  komut_id:
    command: "çalıştırılacak komut"
    console: true/false
    delay: 5
```

## Dosyalar

Plugin şu dosyaları oluşturur:
- `config.yml` - Komut ayarları
- `messages.yml` - Mesaj çevirileri (özelleştirilebilir)
- `players.lc` - Oyuncu giriş verileri (hangi oyuncular daha önce girmiş)

## Placeholder'lar

- `{player}` - Oyuncu adı
- `{uuid}` - Oyuncu UUID'si

## İzinler

- `logincommand.admin` - Plugin yönetimi (varsayılan: OP)

## Dil Desteği

Plugin İngilizce ve Türkçe dillerini destekler. Config'den dil değiştirin:
```yaml
settings:
  language: "tr"  # Türkçe için "tr", İngilizce için "en"
```
# Auction

Dasturni ishga tushirish uchun `db_schema.sql` faylni run qiling, sizni databazangizda dasturga schema paydo boladi. PostgresConnection fayldagi database configini togriling va run qiling.

### Login:
```
http://localhost:8080/login?username=elbek_uzdev&password=elbek2003
``` 
Login qilish uchun post yo'li bilan shunday murajaat qilish kerak, Agar db bolsa:
```json
{
"statusCode": 100,
"description": "authorized",
"data": "ZWxiZWtfdXpkZXYmZWxiZWsyMDAz"
}
```
Bundagi data, key hisoblanadi, keyni headerdan qaytatib backendga jonatish kerak `har doim`.

###  Lot:
Lot qo'shish:
```
http://localhost:8080/lot?model=Tesla model 3&description=Zamonasining eng zo'ri&start_price=50000
```
Shu holatda request borishi kerak (post methodi bilan), yani `model`,`description`, `start_price` paramaterlar borishi kerak, yana bir narsa lotni faqat admin qo'sha oladi. Yuqorida aytganimdek headerda key bilan kelishi kerak.
Agar muvaffaqiyatli qo'shilsa, shundat javob qaytadi: 
```json
{
    "statusCode": 112,
    "description": "successfully saved"
}
```
Lotlarni olish:
```
http://localhost:8080/lot
```
Lotlarni olish uchun yuqoridagidek holatda GET methodi bilan murojaat qilib hamma lotlarni korish mumkin. Bunda ham keyni jonatish kerak, lekin admin ham adminmaslar ham ko'ra oladi. Shu qolibdagi json qaytadi:

```json
{
    "statusCode": 200,
    "description": "ok",
    "data": [
        {
            "id": 2,
            "model": "Tesla model 3",
            "description": "Zamonasining eng zo'ri",
            "startPrice": 50000.0,
            "startDate": "Aug 27, 2022",
            "user": {
                "id": 1,
                "firstname": "Elbek",
                "lastname": "Nurmatov",
                "username": "elbek_uzdev",
                "password": "elbek2003",
                "isAdmin": true
            },
            "isActive": true
        }
    ]
}
```
Lotni o'chirish:
```
http://localhost:8080/lot?lot_id=1
```
Shunday murajjat qilinadi yani, lotni idni jonatiladi DELETE methodi bilan. lotni ochirish uchun admin bolish kerak. Key jonatish kerak. Bundan natija shunday qaytadi yani. Kim lotni yutgon bolsa uni malumot, qancha taklif qilgan bolsa u malomot, hamda lot haqida malumot qaytadi:
```json
{
    "statusCode": 200,
    "description": "ok",
    "data": {
        "id": 0,
        "lot": {
            "id": 0,
            "startPrice": 0.0,
            "isActive": false
        },
        "user": {
            "id": 1,
            "firstname": "Elbek",
            "lastname": "Nurmatov",
            "username": "elbek_uzdev",
            "password": "elbek2003",
            "isAdmin": true
        },
        "price": 56529.56,
        "date": "Aug 27, 2022"
    }
}
```



###  Offer:
Offer qo'shish:
```
http://localhost:8080/offer?lot_id=2&price=4565564
```
Offer qosish uchun shunday POST methodi bilan murojaat qilasiz, key bilan qo'shiladi, va shunday javob qaytadi:
```json
{
    "statusCode": 112,
    "description": "successfully saved"
}
```
Offerlarni olish:
```
http://localhost:8080/offer
```
GET methodi bilan, hamda key bilan murajaat qilinsa qaytaradi, Namuna:
```json
{
    "statusCode": 200,
    "description": "ok",
    "data": [
        {
            "id": 3,
            "lot": {
                "id": 2,
                "model": "Tesla model 3",
                "description": "Zamonasining eng zo'ri",
                "startPrice": 50000.0,
                "startDate": "Aug 27, 2022",
                "user": {
                    "id": 1,
                    "firstname": "Elbek",
                    "lastname": "Nurmatov",
                    "username": "elbek_uzdev",
                    "password": "elbek2003",
                    "isAdmin": true
                },
                "isActive": true
            },
            "user": {
                "id": 2,
                "firstname": "Elbek",
                "lastname": "Nurmatov",
                "username": "elbek_nurmatov",
                "password": "elbek2003",
                "isAdmin": false
            },
            "price": 4565564.0,
            "date": "Aug 27, 2022"
        },
        {
            "id": 1,
            "lot": {
                "id": 0,
                "startPrice": 0.0,
                "isActive": false
            },
            "user": {
                "id": 1,
                "firstname": "Elbek",
                "lastname": "Nurmatov",
                "username": "elbek_uzdev",
                "password": "elbek2003",
                "isAdmin": true
            },
            "price": 56529.56,
            "date": "Aug 27, 2022"
        },
        {
            "id": 2,
            "lot": {
                "id": 0,
                "startPrice": 0.0,
                "isActive": false
            },
            "user": {
                "id": 1,
                "firstname": "Elbek",
                "lastname": "Nurmatov",
                "username": "elbek_uzdev",
                "password": "elbek2003",
                "isAdmin": true
            },
            "price": 56529.56,
            "date": "Aug 27, 2022"
        }
    ]
}
```



import requests
import ujson
import aiohttp
from datetime import date

def date_today() -> str:
    today = date.today()
    return str(today.strftime("%d/%m/%Y"))

def extract_uuid(text: str) -> str:
    return text.split()[1] if len(text.split()) > 1 else None

async def register_user(uuid, chat_id, username, timestamp) -> None:
    async with aiohttp.ClientSession(
            json_serialize=ujson.dumps) as session:
        await session.post(url='http://127.0.0.1:5000/message', json={"uuid": uuid, "chat_id": chat_id, "username": username, "timestamp": timestamp})

async def user_not_subcription(uuid, chat_id):
    async with aiohttp.ClientSession(
            json_serialize=ujson.dumps) as session:
        await session.post(url='http://127.0.0.1:5000/delete_subcription', json={"uuid": uuid, "chat_id": chat_id})
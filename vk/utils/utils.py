import ujson
import aiohttp
from datetime import date

def date_today() -> str:
    today = date.today()
    return str(today.strftime("%d/%m/%Y"))

def extract_uuid(text: str) -> str:
    return text.split()[1] if len(text.split()) > 1 else None
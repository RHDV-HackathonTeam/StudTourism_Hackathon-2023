import logging
import asyncio
import aioschedule
from aiogram import Bot
from aiogram import Dispatcher
from aiogram.fsm.storage.memory import MemoryStorage
from database import dbworker as db
from utils import user_not_subcription

API_TOKEN = '6007918165:AAHnKCElNmchx6xk7KxlXUnTDrIHTJ-Uodc'
WEBHOOK_HOST = 'https://698f-185-82-178-72.eu.ngrok.io'
WEBHOOK_PATH = ''
APP_BASE_URL = f"{WEBHOOK_HOST}{WEBHOOK_PATH}"
MAIN_CHAT_ID = -1001838386158

storage = MemoryStorage()
bot = Bot(token=API_TOKEN)
dp = Dispatcher(storage=storage)

async def check_subscription():
    list = db.get_users()
    for user in list:
        result = await bot.get_chat_member(chat_id=MAIN_CHAT_ID, user_id=user.chat_id)
        if not result.is_member():
            await user_not_subcription(uuid=user.uuid, chat_id=user.chat_id)


async def scheduler():
    aioschedule.every().day.at("12:00").do(check_subscription)

async def on_startup(bot: Bot, base_url: str):
    asyncio.create_task(scheduler())
    await bot.set_webhook(f"{base_url}/webhook")

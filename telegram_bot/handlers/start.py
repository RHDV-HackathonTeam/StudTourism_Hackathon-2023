from aiogram import Router
from aiogram import types
from aiogram.filters.command import Command
from data.keyboard import base_keyboard
from database import dbworker as db
from utils import utils
from data import strings as mp
from config import bot
start_router = Router()

@start_router.message(Command(commands=['start']))
async def start_and_reg(message: types.Message):
    try:
        uuid = utils.extract_uuid(message.text)
        date = utils.date_today()
        if uuid != None:
            register_status = db.init_user(uuid=uuid, chat_id=message.chat.id, username=message.chat.username, notification=True, date_of_join=date)
            if register_status['message'] == 'User already added':
                await bot.send_message(chat_id=message.chat.id, text=mp.already_reg_string, reply_markup=base_keyboard)
            else:
                await bot.send_message(chat_id=message.chat.id, text=mp.hello_string, reply_markup=base_keyboard)
                # await utils.register_user(uuid=uuid, chat_id=message.chat.id, username=message.chat.username, timestamp=date)
    except Exception as e:
        await message.reply(message, f'{e}')
from aiogram import Router
from aiogram import Bot
from aiogram import types
from aiogram.filters.text import Text
from data import keyboard as kb
from database import dbworker as db
from utils import utils
from data import strings as mp
from config import bot, MAIN_CHAT_ID
from aiogram.fsm.state import StatesGroup, State
from aiogram.fsm.context import FSMContext
from aiogram.types.callback_query import CallbackQuery
from aiogram import F
from aiogram.types import URLInputFile
import aiohttp
class Form(StatesGroup):
    check_status = State()

base_router = Router()

@base_router.message(Text(text='Мероприятия'))
async def events(message: types.Message):
    async with aiohttp.ClientSession() as session:
        async with session.get('https://66ec-2a00-1fa0-4a46-2469-7a30-9ca6-609b-b91.eu.ngrok.io/news') as resp:
            data = await resp.json()
            for i in data:
                title = i['title']
                pic_url = i["picture_url"]
                text = i["text"]
                tags = i["tags"]
                caption = title + "\n" + text + "\n"
                for tag in tags:
                    caption += tag + " "
                image = URLInputFile(
                    pic_url
                )
                await bot.send_photo(chat_id=message.chat.id, photo=image, caption=caption[:1024])

    
@base_router.message(Text(text="Настройки"))
async def configs_check(message: types.Message):
    user = db.get_by_id(message.chat.id)
    if not user.notification:
        db.change_user_notice(message.chat.id, True)
        await bot.send_message(chat_id=message.chat.id, text=mp.on_notice_string, reply_markup=kb.off_notice_keyboard)
    else:
        db.change_user_notice(message.chat.id, False)
        await bot.send_message(chat_id=message.chat.id, text=mp.off_notice_string, reply_markup=kb.on_notice_keyboard)

@base_router.callback_query(kb.Moves.filter(F.action == kb.Action.notice))
async def change_notice(query: CallbackQuery, callback_data: kb.Moves, bot: Bot):
    await bot.delete_message(chat_id=query.message.chat.id, message_id=query.message.message_id)
    await bot.send_message(chat_id=query.message.chat.id, text=mp.update_config_string)

@base_router.callback_query(kb.Moves.filter(F.action == kb.Action.back))
async def back_move(query: CallbackQuery, callback_data: kb.Moves, bot: Bot):
    await bot.delete_message(chat_id=query.message.chat.id, message_id=query.message.message_id)

@base_router.message(Text(text='Пригласить'))
async def invite_friend(message: types.Message):
    await bot.send_message(chat_id=message.chat.id, text=mp.moc_string, reply_markup=kb.base_keyboard)

@base_router.message(Text(text='Статус'))
async def check_status(message: types.Message, state: FSMContext):
    check_member = await bot.get_chat_member(chat_id=MAIN_CHAT_ID, user_id=message.chat.id)
    if check_member.status not in ["member", "creator"]:
        await bot.send_message(chat_id=message.chat.id, text=mp.subcribe_string, reply_markup=kb.subscribe_keyboard)
    else:
        await bot.send_message(chat_id=message.chat.id, text=mp.status_string, reply_markup=kb.base_keyboard)

@base_router.callback_query(kb.Moves.filter(F.action == kb.Action.cont))
async def check_user(query: CallbackQuery, callback_data: kb.Moves, bot: Bot):
    check_member = await bot.get_chat_member(chat_id=MAIN_CHAT_ID, user_id=callback_data.chat_id)
    if check_member.status not in ["member", "creator"]:
        await bot.send_message(chat_id=query.message.chat.id, text=mp.subcribe_string, reply_markup=kb.subscribe_keyboard)
    else:
        await bot.send_message(chat_id=query.message.chat.id, text=mp.status_string, reply_markup=kb.base_keyboard)

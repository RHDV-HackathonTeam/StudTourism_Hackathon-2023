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
from aiogram.utils.keyboard import InlineKeyboardBuilder
from aiogram.types.callback_query import CallbackQuery
from aiogram import F

class Form(StatesGroup):
    check_status = State()

base_router = Router()

@base_router.message(Text(text='Мероприятия'))
async def events(message: types.Message):
    await bot.send_message(chat_id=message.chat.id, text=mp.hello_string, reply_markup=kb.base_keyboard)

@base_router.message(Text(text="Настройки"))
async def configs_check(message: types.Message):
    builder = InlineKeyboardBuilder()
    user = db.get_by_id(message.chat.id)
    if not user.notification:
        builder.button(text=mp.off_string, callback_data=kb.Moves(action=kb.Action.notice, chat_id=message.chat.id, user_id=message.chat.id, message_id=message.message_id))
        builder.button(text=mp.back_string, callback_data=kb.Moves(action=kb.Action.back, chat_id=message.chat.id, user_id=message.chat.id, message_id=message.message_id))
        builder.adjust(1, 2)
        db.change_user_notice(message.chat.id, True)
        await bot.send_message(chat_id=message.chat.id, text=mp.on_notice_string, reply_markup=builder.as_markup())
    else:
        builder.button(text=mp.on_string, callback_data=kb.Moves(action=kb.Action.notice, chat_id=message.chat.id, user_id=message.chat.id, message_id=message.message_id))
        builder.button(text=mp.back_string, callback_data=kb.Moves(action=kb.Action.back, chat_id=message.chat.id, user_id=message.chat.id, message_id=message.message_id))
        builder.adjust(1, 2)
        db.change_user_notice(message.chat.id, False)
        await bot.send_message(chat_id=message.chat.id, text=mp.off_notice_string, reply_markup=builder.as_markup())

@base_router.callback_query(kb.Moves.filter(F.action == kb.Action.notice))
async def change_notice(query: CallbackQuery, callback_data: kb.Moves, bot: Bot):
    await bot.delete_message(chat_id=callback_data.chat_id, message_id=query.message.message_id)
    await bot.send_message(chat_id=callback_data.user_id, text=mp.update_config_string)

@base_router.callback_query(kb.Moves.filter(F.action == kb.Action.back))
async def back_move(query: CallbackQuery, callback_data: kb.Moves, bot: Bot):
    await bot.delete_message(chat_id=callback_data.chat_id, message_id=query.message.message_id)

@base_router.message(Text(text='Пригласить'))
async def events(message: types.Message):
    await bot.send_message(chat_id=message.chat.id, text=mp.hello_string, reply_markup=kb.base_keyboard)

@base_router.message(Text(text='Статус'))
async def events(message: types.Message, state: FSMContext):
    check_member = await bot.get_chat_member(chat_id=MAIN_CHAT_ID, user_id=message.chat.id)
    if check_member.status not in ["member", "creator"]:
        builder = InlineKeyboardBuilder()
        builder.button(text="Подписаться", url=mp.url_channel)
        builder.button(text="Подтвердить", callback_data=kb.Moves(action=kb.Action.cont, chat_id=message.chat.id, user_id=message.chat.id))
        builder.adjust(1, 2)
        await bot.send_message(chat_id=message.chat.id, text=mp.subcribe_string, reply_markup=builder.as_markup())
    else:
        await bot.send_message(chat_id=message.chat.id, text=mp.status_string, reply_markup=kb.base_keyboard)

@base_router.callback_query(kb.Moves.filter(F.action == kb.Action.cont))
async def check_user(query: CallbackQuery, callback_data: kb.Moves, bot: Bot):
    check_member = await bot.get_chat_member(chat_id=MAIN_CHAT_ID, user_id=callback_data.chat_id)
    if check_member.status not in ["member", "creator"]:
        builder = InlineKeyboardBuilder()
        builder.button(text="Подписаться", url=mp.url_channel)
        builder.button(text="Подтвердить", callback_data=kb.Moves(action=kb.Action.cont,chat_id=callback_data.chat_id, user_id=callback_data.chat_id))
        builder.adjust(1, 2)
        await bot.send_message(chat_id=callback_data.chat_id, text=mp.subcribe_string, reply_markup=builder.as_markup())
    else:
        await bot.send_message(chat_id=callback_data.chat_id, text=mp.status_string, reply_markup=kb.base_keyboard)

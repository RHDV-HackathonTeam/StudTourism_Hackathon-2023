from aiogram.types import KeyboardButton, ReplyKeyboardMarkup, InlineKeyboardButton, InlineKeyboardMarkup
from aiogram.filters.callback_data import CallbackData
from enum import Enum
base_kb = [
        [
            KeyboardButton(text="Мероприятия"),
            KeyboardButton(text="Настройки")
        ],
        [
            KeyboardButton(text="Пригласить"),
            KeyboardButton(text="Статус")
        ]
    ]
base_keyboard = ReplyKeyboardMarkup(
    keyboard=base_kb,
    resize_keyboard=True
)

class Action(str, Enum):
    cont = "cont"
    notice = "notice"
    back = "back"
class Moves(CallbackData, prefix="cont"):
    action: Action
    chat_id: int
    user_id: int
    message_id: int
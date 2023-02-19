from aiogram.types import KeyboardButton, ReplyKeyboardMarkup, InlineKeyboardButton, InlineKeyboardMarkup
from aiogram.filters.callback_data import CallbackData
from data import strings as mp
from enum import Enum

class Action(str, Enum):
    cont = "cont"
    notice = "notice"
    back = "back"

class Moves(CallbackData, prefix="cont"):
    action: Action

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

subscribe_kb = [
        [
            InlineKeyboardButton(text="Подписаться", url=mp.url_channel)
        ],
        [
            InlineKeyboardButton(text="Подтвердить", callback_data=Moves(action=Action.cont).pack())
        ]
]

subscribe_keyboard = InlineKeyboardMarkup(inline_keyboard=subscribe_kb)

on_notice_kb = [
        [
            InlineKeyboardButton(text=mp.on_string, callback_data=Moves(action=Action.notice).pack())
        ],
        [
            InlineKeyboardButton(text=mp.back_string, callback_data=Moves(action=Action.back).pack())
        ]
]

on_notice_keyboard = InlineKeyboardMarkup(inline_keyboard=on_notice_kb)

off_notice_kb = [
        [
            InlineKeyboardButton(text=mp.off_string, callback_data=Moves(action=Action.notice).pack())
        ],
        [
            InlineKeyboardButton(text=mp.back_string, callback_data=Moves(action=Action.back).pack())
        ]
]

off_notice_keyboard = InlineKeyboardMarkup(inline_keyboard=off_notice_kb)

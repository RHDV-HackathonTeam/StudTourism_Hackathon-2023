from aiohttp.web_request import Request
from aiohttp.web_response import json_response
from database import dbworker as db, Users
from aiogram import Bot

async def check_data_handler(request: Request):
    bot: Bot = request.app["bot"]
    data = await request.post()
    list = db.get_users()
    for user in list:
        if user.notification:
            await bot.send_message(chat_id=user.chat_id, text=data['text'])
    return json_response({"ok": True})
import asyncio
from aiohttp.web import run_app
from aiohttp.web_app import Application
from config import dp, bot, on_startup, APP_BASE_URL
from handlers import start_router, base_router
from aiogram.webhook.aiohttp_server import SimpleRequestHandler, setup_application
from routes import check_data_handler

def main():
    dp["base_url"] = APP_BASE_URL
    dp.startup.register(on_startup)

    dp.include_router(start_router)
    dp.include_router(base_router)

    app = Application()
    app["bot"] = bot

    app.router.add_post("/news", check_data_handler)
    SimpleRequestHandler(
        dispatcher=dp,
        bot=bot,
    ).register(app, path="/webhook")
    setup_application(app, dp, bot=bot)

    run_app(app, host="127.0.0.1", port=3001)

if __name__ == '__main__':
    main()
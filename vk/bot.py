import logging
from vkbottle import Bot
from handlers import start_labeler, base_labeler
from config import api, state_dispenser, labeler, callback
from fastapi import BackgroundTasks, FastAPI, Request, Response
from vkbottle import PhotoMessageUploader

logging.basicConfig(level=logging.DEBUG)

app = FastAPI()

labeler.load(start_labeler)
labeler.load(base_labeler)

bot = Bot(
    api=api,
    labeler=labeler,
    state_dispenser=state_dispenser,
    callback=callback
)
photo_uploader = PhotoMessageUploader(bot.api)

confirmation_code: str
secret_key: str


@app.on_event("startup")
async def startup_event():
    logging.info("Setup webhook")
    global confirmation_code, secret_key
    confirmation_code, secret_key = await bot.setup_webhook()

@app.post("/callback")
async def vk_handler(req: Request, background_task: BackgroundTasks):
    global confirmation_code, secret_key
    try:
        data = await req.json()
    except Exception:
        logging.warning("Empty request")
        return Response("not today", status_code=403)

    if data["type"] == "confirmation":
        logging.info("Send confirmation token: {}", confirmation_code)
        return Response(confirmation_code)

    # If the secrets match, then the message definitely came from our bot
    if data["secret"] == secret_key:
        # Running the process in the background, because the logic can be complicated
        background_task.add_task(bot.process_event, data)
    return Response("ok")

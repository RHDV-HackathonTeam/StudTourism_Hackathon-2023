from vkbottle import API, BuiltinStateDispenser, Bot
from vkbottle.bot import BotLabeler
from vkbottle.callback import BotCallback

TOKEN='vk1.a.SAkvRvQwrhIRJoTXjn8HKxSs5UPEPQIsCmQDX9j1qUSjrPnoo_l1ohaJ4NuQnfoWnyWlgwwVwLO4nTkTqio7ZdExfKvypLOCYKuchwT_Waghi1NjBvZ82YNzL_fs0zfVP2Q0Z3TTq7sp5wTs9yJPPHvHBK4Zb6jBhRONFgOK5bvgxf06oUQK_dcOlPpMPIskr4cjxIGlTfKSA6dI26zyog'
url='https://93ed-185-82-178-67.eu.ngrok.io/callback'
title='server'
api = API(token=TOKEN)
labeler = BotLabeler()
state_dispenser = BuiltinStateDispenser()
callback = BotCallback(
    url = url,
    title = title
)
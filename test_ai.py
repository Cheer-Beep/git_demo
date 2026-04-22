from langchain.chat_models import init_chat_model
from langchain_tavily import TavilySearch
from langchain.agents import create_agent
import os


#加载环境变量
from dotenv import load_dotenv
load_dotenv()

#定义多模态模型
multimodal_model = init_chat_model(
    model= 'qwen3.6-plus',
    model_provider = 'openai',
    base_url = os.getenv('DASHSCOPE_BASE_URL'),
    api_key = os.getenv('DASHSCOPE_API_KEY'),
)

#web搜索工具，使用tavily作为web搜索工具
web_search = TavilySearch(
    max_results=5,
    topic = 'general',
)

#定义智能体
#系统提示词
system_prompt = """
你是一个智能体，能够使用工具来完成任务。你可以使用以下工具：
1. Web Search: 你可以使用这个工具来搜索互联网以获取最新的信息。
2. 其他工具：你可以根据需要使用其他工具来完成任务。
当你需要使用工具时，请明确说明你要使用哪个工具，并提供必要的输入。
完成工具使用后，请总结你的发现并继续完成任务。请确保你的回答清晰、简洁，并且直接回答用户的问题。

"""
agent = create_agent(
    model=multimodal_model,
    tools=[web_search],
    system_prompt=system_prompt
)


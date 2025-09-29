<template>
  <div class="app-layout">
    <div class="sidebar">
      <div class="logo-section">
        <img src="@/assets/vue.svg" alt="Zoe大模型" width="160" height="160" />
       <!-- <img src="@/assets/zoe.jpg" alt="Zoe大模型" width="160" height="160" />-->

        <span class="logo-text">尚平小智(医疗)</span>
      </div>
      <el-button class="new-chat-button" @click="newChat">
        <i class="fa-solid fa-plus"></i>
        &nbsp;新会话
      </el-button>
    </div>
    <div class="main-content">
      <div class="chat-container">
        <div class="message-list" ref="messaggListRef">
          <div
              v-for="(message, index) in messages"
              :key="index"
              :class="
              message.isUser ? 'message user-message' : 'message bot-message'
            "
          >
            <!-- 会话图标 -->
            <i
                :class="
                message.isUser
                  ? 'fa-solid fa-user message-icon'
                  : 'fa-solid fa-robot message-icon'
              "
            ></i>
            <!-- 会话内容 -->
            <span class="message-content">
              <span v-html="message.content"></span>
              <!-- loading -->
              <span
                  class="loading-dots"
                  v-if="message.isThinking || message.isTyping"
              >
                <span class="dot"></span>
                <span class="dot"></span>
              </span>
            </span>
          </div>
        </div>
        <div class="input-container">
          <el-input
              v-model="inputMessage"
              placeholder="请输入消息"
              @keyup.enter="sendMessage"
          ></el-input>
          <el-button @click="sendMessage" :disabled="isSending" type="primary">
            发送
          </el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref, watch } from 'vue';
import axios from 'axios';
import { v4 as uuidv4 } from 'uuid';

const messaggListRef = ref();
const isSending = ref(false);
const uuid = ref();
const inputMessage = ref('');
const messages = ref([]);

onMounted(() => {
  initUUID();
  // 移除 setInterval，改用手动滚动
  watch(messages, () => scrollToBottom(), { deep: true });
  hello();
});

const scrollToBottom = () => {
  if (messaggListRef.value) {
    messaggListRef.value.scrollTop = messaggListRef.value.scrollHeight;
  }
};

const hello = () => {
  sendRequest('你好');
};

const sendMessage = () => {
  if (inputMessage.value.trim()) {
    sendRequest(inputMessage.value.trim());
    inputMessage.value = '';
  }
};

const sendRequest = (message) => {
  isSending.value = true;
  const userMsg = {
    isUser: true,
    content: message,
    isTyping: false,
    isThinking: false,
  };
  // 第一条默认发送的用户消息”你好“不放入会话列表
  if (messages.value.length > 0) {
    messages.value.push(userMsg);
  }

  // 添加机器人加载消息
  const botMsg = {
    isUser: false,
    content: '', // 增量填充
    isTyping: true, // 显示加载动画
    isThinking: false,
  };
  messages.value.push(botMsg);
  const lastMsg = messages.value[messages.value.length - 1];
  scrollToBottom();

  axios
      .post('/api/sp/chat', { memoryId: uuid.value, message }, {
        responseType: 'stream',
        onDownloadProgress: (e) => {
          const fullText = e.event.target.responseText;
          let newText = fullText.substring(lastMsg.content.length);
          lastMsg.content += newText;
          console.log(lastMsg);
          scrollToBottom(); // 实时滚动
        },
      })
      .then(() => {
        messages.value.at(-1).isTyping = false;
        isSending.value = false;
      })
      .catch((error) => {
        console.error('流式错误:', error);
        messages.value.at(-1).content = '请求失败，请重试';
        messages.value.at(-1).isTyping = false;
        isSending.value = false;
      });
};

// 初始化 UUID
const initUUID = () => {
  let storedUUID = localStorage.getItem('user_uuid');
  if (!storedUUID) {
    storedUUID = uuidToNumber(uuidv4());
    localStorage.setItem('user_uuid', storedUUID);
  }
  uuid.value = storedUUID;
};

const uuidToNumber = (uuid) => {
  let number = 0;
  for (let i = 0; i < uuid.length && i < 6; i++) {
    const hexValue = uuid[i];
    number = number * 16 + (parseInt(hexValue, 16) || 0);
  }
  return number % 1000000;
};

const newChat = () => {
  console.log('开始新会话');
  localStorage.removeItem('user_uuid');
  window.location.reload();
};
</script>

<style scoped>
.app-layout {
  display: flex;
  height: 100vh;
}

.sidebar {
  width: 220px;
  background-color: #f9f9fb;
  padding: 25px;
  display: flex;
  flex-direction: column;
  align-items: center;
  border-right: 1px solid #ddd;
}

.logo-section {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.logo-text {
  font-size: 20px;
  font-weight: bold;
  margin-top: 12px;
  color: #333;
}

.new-chat-button {
  width: 100%;
  margin-top: 50px;
  background: linear-gradient(to right, #4facfe, #00f2fe);
  color: white;
  border: none;
  transition: transform 0.2s ease;
}

.new-chat-button:hover {
  transform: scale(1.03);
}

.main-content {
  flex: 1;
  padding: 20px;
  overflow-y: auto;
  background-color: #f5f7fa;
}

.chat-container {
  display: flex;
  flex-direction: column;
  height: 100%;
}

.message-list {
  flex: 1;
  overflow-y: auto;
  padding: 15px;
  border: 1px solid #e8e8ee;
  border-radius: 8px;
  background-color: #fff;
  margin-bottom: 15px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
  display: flex;
  flex-direction: column;
}

.message {
  margin-bottom: 20px;
  padding: 12px 20px;
  border-radius: 8px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  display: flex;
  align-items: start;
}

.message-content {
  font-size: 20px;
  line-height: 1.5;
  word-break: break-word;
}

.user-message {
  max-width: 85%;
  background-color: #a5d4ef;
  align-self: flex-end;
  border-top-right-radius: 4px;
}

.bot-message {
  max-width: 70%;
  background-color: #a5d4ef;
  align-self: flex-start;
  border-top-left-radius: 4px;
}

.message-icon {
  margin: 0 10px;
  font-size: 1.2em;
  color: #555;
}

.loading-dots {
  padding-left: 5px;
}

.dot {
  display: inline-block;
  margin-left: 5px;
  width: 8px;
  height: 8px;
  background-color: #000000;
  border-radius: 50%;
  animation: pulse 1.2s infinite ease-in-out both;
}

.dot:nth-child(2) {
  animation-delay: -0.6s;
}

@keyframes pulse {
  0%,
  100% {
    transform: scale(0.6);
    opacity: 0.4;
  }

  50% {
    transform: scale(1);
    opacity: 1;
  }
}

.input-container {
  display: flex;
  align-items: center;
}

.input-container .el-input {
  flex: 1;
  margin-right: 10px;
  border-radius: 8px;
  height: 100px; /* 设置为你想要的高度 */
}

.input-container .el-button {
  height: 50px; /* 设置按钮高度 */
  font-size: 16px; /* 设置字体大小 */
  padding: 0 20px; /* 可选：调整按钮内边距 */
  background: linear-gradient(to right, #4facfe, #00f2fe);
  color: white;
  border: none;
  transition: all 0.3s ease;
}

.input-container .el-button:hover {
  transform: scale(1.05);
}

/* 响应式设计 */
@media (max-width: 768px) {
  .main-content {
    padding: 10px 0 10px 0;
  }

  .app-layout {
    flex-direction: column;
  }

  .sidebar {
    width: 100%;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    padding: 10px;
    border-right: none;
    border-bottom: 1px solid #ddd;
  }

  .logo-section {
    flex-direction: row;
    align-items: center;
  }

  .logo-text {
    font-size: 18px;
  }

  .logo-section img {
    width: 40px;
    height: 40px;
  }

  .new-chat-button {
    margin-right: 20px;
    width: auto;
    margin-top: 5px;
  }
}

@media (min-width: 769px) {
  .main-content {
    padding: 0 0 10px 10px;
  }

  .app-layout {
    display: flex;
    height: 100vh;
  }

  .sidebar {
    width: 220px;
    background-color: #f9f9fb;
    padding: 25px;
    display: flex;
    flex-direction: column;
    align-items: center;
    border-right: 1px solid #ddd;
  }

  .logo-section {
    display: flex;
    flex-direction: column;
    align-items: center;
  }

  .logo-text {
    font-size: 20px;
    font-weight: bold;
    margin-top: 12px;
    color: #333;
  }

  .new-chat-button {
    width: 100%;
    margin-top: 25px;
    background: linear-gradient(to right, #4facfe, #00f2fe);
    color: white;
    border: none;
    transition: transform 0.2s ease;
  }

  .new-chat-button:hover {
    transform: scale(1.03);
  }
}
</style>

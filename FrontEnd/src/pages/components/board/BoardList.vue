<template>
  <div>
    <div class="page-header clear-filter" filter-color="blue">
      <b-container class="bv-example-row mt-3">
        <div class="d-flex justify-content-between">
          <div class="d-flex align-items-center" style="font-size: 2rem">
            <b-icon icon="journals"></b-icon>
            <span> Q&A</span>
          </div>
          <b-button @click="moveWrite" class="btn-neutral text-info">글쓰기</b-button>
        </div>
        <b-row>
          <b-col>
            <b-table striped hover :items="articles" :fields="fields" @row-clicked="viewArticle">
              <template #cell(subject)="data">
                <router-link
                  :to="{
                    name: 'boardview',
                    params: { articleNo: data.item.articleNo },
                  }"
                >
                  {{ data.item.subject }}
                </router-link>
              </template>
            </b-table>
          </b-col>
        </b-row>
      </b-container>
    </div>
  </div>
</template>

<script>
import { getArticleList } from "@/api/board";
import { mapState } from "vuex";

const userStore = "userStore";

export default {
  name: "BoardList",
  data() {
    return {
      articles: [],
      fields: [
        { key: "articleNo", label: "글번호", tdClass: "tdClass" },
        { key: "subject", label: "제목", tdClass: "tdSubject" },
        { key: "userId", label: "작성자", tdClass: "tdClass" },
        { key: "regTime", label: "작성일", tdClass: "tdClass" },
        // { key: "hit", label: "조회수", tdClass: "tdClass" },
      ],
      // isLogin: null
    };
  },
  created() {
    let param = {
      pg: 1,
      spp: 20,
      key: null,
      word: null,
    };
    getArticleList(
      param,
      ({ data }) => {
        this.articles = data.boardList;
      },
      (error) => {
        console.log(error);
      }
    );
  },
  computed: {
    ...mapState(userStore, ["userInfo"]),
  },
  methods: {
    async changePro() {
      this.user.userId = this.userInfo.userId;
      await this.changeProfile(this.user);
      this.$router.push("/profile");
    },
    moveWrite() {
      if (this.userInfo) {
        this.$router.push({ name: "boardwrite" });
      } else {
        alert("로그인이 필요합니다.");
      }
    },
    viewArticle(article) {
      this.$router.push({
        name: "boardview",
        params: { articleNo: article.articleNo },
      });
    },
  },
};
</script>

<style scope>
.tdClass {
  width: 50px;
  text-align: center;
}
.tdSubject {
  width: 300px;
  text-align: left;
}
</style>

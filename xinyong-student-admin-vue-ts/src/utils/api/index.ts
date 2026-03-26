// 接口大全对象
export const api: any = {};

// 导入所有的接口信息
const modules: any = import.meta.glob('@/api/**/index.ts', { eager: true });
const toMap: any = (obj: any, arr: Array<string>, data: any) => {
  if (arr.length === 0) {
    return obj;
  }
  const [head, ...tail] = arr;
  if (!obj[head]) {
    if (tail.length == 0) {
      obj[head] = data ?? {};
    } else {
      obj[head] = {};
    }
  }
  return toMap(obj[head], tail, data);
};

for (const item in modules) {
  const regex = /\/src\/api\/(.+?)\/index\.ts/;
  const match = item.match(regex);
  if (match) {
    let d: any = {};
    for (const f in modules[item]) {
      d[f] = modules[item][f];
    }
    const res = match[1].split('/');
    toMap(api, res, d);
  }
}

import jsPDF from "jspdf";
import html2canvas from "html2canvas";
import { tt } from "@/utils/tool";

export interface TijianReportData {
  userName: string;
  createTime: string;
  qiyeName: string;
  hospitalName: string;
  itemJson: string;
  allMoney: string | number;
  result: string;
  hash: string;
  tx: string;
}

/**
 * 通用PDF导出方法
 * @param elementOrHtml DOM元素或HTML字符串
 * @param fileName 文件名（可选，如果为空则直接打开）
 */
const exportPdfFromHtml = async (
  elementOrHtml: HTMLElement | string,
  fileName?: string,
) => {
  let container: HTMLElement | null = null;
  let targetElement: HTMLElement;

  if (typeof elementOrHtml === "string") {
    container = document.createElement("div");
    container.style.position = "absolute";
    container.style.top = "-9999px";
    container.style.left = "-9999px";
    container.style.width = "794px"; // A4 width
    container.style.backgroundColor = "#fff";
    container.style.zIndex = "9999";
    container.innerHTML = elementOrHtml;
    document.body.appendChild(container);
    targetElement = container;
  } else {
    targetElement = elementOrHtml;
  }

  try {
    const canvas = await html2canvas(targetElement, {
      scale: 2,
      useCORS: true,
      logging: false,
    });

    const imgData = canvas.toDataURL("image/jpeg", 1.0);
    const pdf = new jsPDF("p", "mm", "a4");
    const pdfWidth = pdf.internal.pageSize.getWidth();
    const pdfHeight = pdf.internal.pageSize.getHeight();
    const imgProps = pdf.getImageProperties(imgData);
    const imgHeight = (imgProps.height * pdfWidth) / imgProps.width;

    pdf.addImage(imgData, "JPEG", 0, 0, pdfWidth, imgHeight);

    if (fileName) {
      pdf.save(fileName);
    } else {
      const pdfBlob = pdf.output("blob");
      const pdfUrl = URL.createObjectURL(pdfBlob);
      window.open(pdfUrl, "_blank");
    }
  } catch (error) {
    console.error("Export PDF failed", error);
    tt.msg.error("导出PDF失败");
    throw error;
  } finally {
    if (container) {
      document.body.removeChild(container);
    }
  }
};

export const pdfService = {
  /**
   * 导出体检报告PDF
   * @param data 报告数据
   */
  async exportTijianReport(data: TijianReportData) {
    const htmlContent = `
      <div style="padding: 40px; font-family: 'Microsoft YaHei', sans-serif; color: #333;">
          <h1 style="text-align: center; font-size: 28px; margin-bottom: 40px; font-weight: bold;">体检报告</h1>
          
          <table style="width: 100%; border-collapse: collapse; margin-bottom: 30px; font-size: 14px;">
              <tr>
                  <td style="padding: 12px; border: 1px solid #ebeef5; background-color: #f5f7fa; width: 120px; font-weight: bold;">用户姓名</td>
                  <td style="padding: 12px; border: 1px solid #ebeef5;">${data.userName}</td>
                  <td style="padding: 12px; border: 1px solid #ebeef5; background-color: #f5f7fa; width: 120px; font-weight: bold;">创建时间</td>
                  <td style="padding: 12px; border: 1px solid #ebeef5;">${data.createTime}</td>
              </tr>
              <tr>
                  <td style="padding: 12px; border: 1px solid #ebeef5; background-color: #f5f7fa; font-weight: bold;">关联企业</td>
                  <td style="padding: 12px; border: 1px solid #ebeef5;">${data.qiyeName}</td>
                  <td style="padding: 12px; border: 1px solid #ebeef5; background-color: #f5f7fa; font-weight: bold;">关联医院</td>
                  <td style="padding: 12px; border: 1px solid #ebeef5;">${data.hospitalName}</td>
              </tr>
          </table>

          <div style="margin-bottom: 30px;">
              <h3 style="border-left: 4px solid #409EFF; padding-left: 10px; margin-bottom: 20px; font-size: 16px; font-weight: bold;">体检详情</h3>
              <table style="width: 100%; border-collapse: collapse; font-size: 14px;">
                  <tr>
                      <td style="padding: 12px; border: 1px solid #ebeef5; background-color: #f5f7fa; width: 120px; font-weight: bold;">体检项目</td>
                      <td style="padding: 12px; border: 1px solid #ebeef5;">${data.itemJson}</td>
                  </tr>
                  <tr>
                      <td style="padding: 12px; border: 1px solid #ebeef5; background-color: #f5f7fa; font-weight: bold;">花费金额</td>
                      <td style="padding: 12px; border: 1px solid #ebeef5;">${data.allMoney}</td>
                  </tr>
                  <tr>
                      <td style="padding: 12px; border: 1px solid #ebeef5; background-color: #f5f7fa; font-weight: bold;">体检结果</td>
                      <td style="padding: 12px; border: 1px solid #ebeef5;">${data.result}</td>
                  </tr>
              </table>
          </div>

          <div style="margin-bottom: 20px;">
              <h3 style="border-left: 4px solid #409EFF; padding-left: 10px; margin-bottom: 20px; font-size: 16px; font-weight: bold;">区块链信息</h3>
              <div style="background-color: #f5f7fa; padding: 20px; border-radius: 4px; word-break: break-all; font-size: 12px; color: #606266;">
                  <p style="margin: 8px 0;"><strong>区块Hash:</strong> ${data.hash}</p>
                  <p style="margin: 8px 0;"><strong>交易Hash:</strong> ${data.tx}</p>
              </div>
          </div>
      </div>
    `;

    await exportPdfFromHtml(htmlContent);
  },

  // 暴露通用方法，以便其他地方使用
  exportPdfFromHtml,
};

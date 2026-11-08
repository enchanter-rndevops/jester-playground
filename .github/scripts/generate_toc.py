import os
import re
from pathlib import Path

# 設定
TEMPLATE_PATH = "template.md"
OUTPUT_PATH = "index.md"  # 出力ファイル名（必要に応じて変更可）
INJECT_TAG = "<!-- TOC_INJECT -->"

def extract_first_h1(file_path):
    """Markdownファイルから最初のH1見出しを抽出する"""
    try:
        with open(file_path, "r", encoding="utf-8") as f:
            for line in f:
                # "# " で始まる行をH1として取得
                if line.startswith("# "):
                    return line[2:].strip()
    except Exception as e:
        print(f"Error reading {file_path}: {e}")
    return None

def generate_toc():
    root_dir = Path(".")
    markdown_files = []

    # すべてのフォルダから .md ファイルを探す（自分自身や出力ファイル、テンプレートは除外）
    exclude_files = {Path(TEMPLATE_PATH), Path(OUTPUT_PATH), Path("README.md")}
    
    for path in root_dir.rglob("*.md"):
        # .github などの隠しフォルダや除外ファイルを除外
        if any(part.startswith(".") for part in path.parts):
            continue
        #　node_modules ディレクトリを含むものを除外
        if "node_modules" in path.parts:
            continue            
        # 指定した個別ファイルを除外
        if path.resolve() in [f.resolve() for f in exclude_files]:
            continue
        markdown_files.append(path)

    # パス順にソート
    markdown_files.sort()

    toc_lines = []
    for md_path in markdown_files:
        h1_title = extract_first_h1(md_path)
        # H1が見つからない場合はファイル名をフォールバックとして使用
        title = h1_title if h1_title else md_path.name
        
        # リポジトリルートからの相対パスをスラッシュ区切りに変換（Markdownリンク用）
        rel_path = md_path.as_posix()
        
        toc_lines.append(f"- [{title}]({rel_path})")

    toc_content = "\n".join(toc_lines)

    # テンプレートを読み込んでマージ
    if os.path.exists(TEMPLATE_PATH):
        with open(TEMPLATE_PATH, "r", encoding="utf-8") as f:
            template_data = f.read()
        
        if INJECT_TAG in template_data:
            final_content = template_data.replace(INJECT_TAG, toc_content)
        else:
            # プレースホルダーがない場合は末尾に追加
            final_content = template_data + "\n\n" + toc_content
    else:
        # テンプレートがない場合は目次のみを出力
        final_content = f"# 目次\n\n{toc_content}\n"

    with open(OUTPUT_PATH, "w", encoding="utf-8") as f:
        f.write(final_content)
    
    print(f"Successfully generated {OUTPUT_PATH}")

if __name__ == "__main__":
    generate_toc()
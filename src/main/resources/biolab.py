import os
import requests
import argparse
import click

@click.group()
def cli():
    pass

@cli.command()
@click.argument('directory')
@click.option('--url', required=True, help='Target URL')
@click.option('--method', default='POST', help='HTTP method')
@click.option('--extensions', '-e', multiple=True, help='File extensions')
def scan(directory, url, method, extensions):
    scan_and_send_requests(directory, url, method, extensions);

def scan_and_send_requests(directory, url, method, extensions):
    for root, _, files in os.walk(directory):
        for file in files:
            if extensions and not file.endswith(tuple(extensions)):
                continue
            file_path = os.path.join(root, file)
            response = requests.request(method, url, data=file_content, headers=headers)

@cli.command()
@click.argument('server-url' , required = True, help = 'Server url info, eg: https://biolab.cn:8080')
def config():
    # 配置服务端信息
    home_dir = os.path.expanduser('~')

    print("home dir----",home_dir)

@cli.command()
@click.argument('directory')
@click.option('--url', required=True, help='Target URL')
@click.option('--method', default='POST', help='HTTP method')
@click.option('--extensions', '-e', multiple=True, help='File extensions')
def regIndex():
    # 注册索引数据
    print("config----")

@cli.command()
def results():
    print("results----")

if __name__ == '__main__':
    cli()